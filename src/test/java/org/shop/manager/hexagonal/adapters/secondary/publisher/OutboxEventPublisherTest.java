package org.shop.manager.hexagonal.adapters.secondary.publisher;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.vocabulary.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Tag("persistence")
public class OutboxEventPublisherTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OutboxEventPublisher eventPublisher;

    @MockBean
    private KafkaTemplate<String, Event> kafkaTemplate;

    @Test
    void storesEventInOutboxTable() {
        var event = anEvent();

        eventPublisher.publish(event);

        List<JpaEvent> fromJpaEvent = entityManager.createQuery("from JpaEvent", JpaEvent.class).getResultList();
        Assertions.assertThat(fromJpaEvent)
                .hasSize(1)
                .allSatisfy(input -> {
                            Assertions.assertThat(input.getData()).isEqualTo(event);
                            Assertions.assertThat(input.getStatus()).isEqualTo(EventStatus.UNPROCESSED);
                        }
                );
    }

    @Test
    void eventsAreProcessed() {
        var event = anEvent();

        JpaEvent jpaEvent = getJpaEvent(event);
        jpaEvent.setStatus(EventStatus.UNPROCESSED);
        entityManager.persist(jpaEvent);

        eventPublisher.processEvents();

        verify(kafkaTemplate).send(eventPublisher.getTopicName(), event);
    }

    @Test
    void unprocessableEventsAreSetInFailedStatue() {
        var event = anEvent();

        JpaEvent jpaEvent = getJpaEvent(event);
        jpaEvent.setStatus(EventStatus.UNPROCESSED);
        entityManager.persist(jpaEvent);

        doThrow(RuntimeException.class).when(kafkaTemplate).send(eventPublisher.getTopicName(), event);

        eventPublisher.processEvents();

        List<JpaEvent> fromJpaEvent = entityManager.createQuery("from JpaEvent", JpaEvent.class).getResultList();
        Assertions.assertThat(fromJpaEvent)
                .hasSize(1)
                .allSatisfy(input -> {
                            Assertions.assertThat(input.getData()).isEqualTo(event);
                            Assertions.assertThat(input.getStatus()).isEqualTo(EventStatus.FAILED);
                        }
                );
    }

    @Test
    void processedEventAreCleanedUp() {
        var event = anEvent();

        JpaEvent jpaEvent = getJpaEvent(event);
        jpaEvent.setStatus(EventStatus.PROCESSED);
        entityManager.persist(jpaEvent);

        eventPublisher.cleanUpEvents();

        List<JpaEvent> fromJpaEvent = entityManager.createQuery("from JpaEvent", JpaEvent.class).getResultList();
        Assertions.assertThat(fromJpaEvent)
                .isEmpty();
    }

    @NotNull
    private TestEvent anEvent() {
        return new TestEvent("msg");
    }

    @NotNull
    private JpaEvent getJpaEvent(TestEvent event) {
        JpaEvent jpaEvent = new JpaEvent();
        jpaEvent.setId("id");
        jpaEvent.setData(event);
        return jpaEvent;
    }

    public record TestEvent(String message) implements Event {
    }
}
