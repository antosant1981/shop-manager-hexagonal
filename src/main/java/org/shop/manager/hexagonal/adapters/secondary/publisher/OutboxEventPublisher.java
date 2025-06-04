package org.shop.manager.hexagonal.adapters.secondary.publisher;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.shop.manager.hexagonal.domain.EventPublisher;
import org.shop.manager.hexagonal.vocabulary.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OutboxEventPublisher implements EventPublisher {
    private final JpaEventOutboxRepository jpaEventOutboxRepository;
    private final KafkaTemplate<String, Event> kafkaTemplate;
    @Getter
    private final String topicName;

    public OutboxEventPublisher(JpaEventOutboxRepository jpaEventOutboxRepository,
                                KafkaTemplate<String, Event> kafkaTemplate,
                                @Value("${kafka.producer.outbox.topic}") String topicName) {
        this.jpaEventOutboxRepository = jpaEventOutboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publish(Event event) {
        JpaEvent jpaEvent = new JpaEvent();
        jpaEvent.setId(UUID.randomUUID().toString());
        jpaEvent.setData(event);
        jpaEvent.setStatus(EventStatus.UNPROCESSED);
        jpaEventOutboxRepository.saveAndFlush(jpaEvent);
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void processEvents() {
        jpaEventOutboxRepository.findByStatus(EventStatus.UNPROCESSED)
                .forEach(this::processEvent);
    }

    private void processEvent(JpaEvent jpaEvent) {
        try {
            kafkaTemplate.send(topicName, jpaEvent.getData());
            jpaEvent.setStatus(EventStatus.PROCESSED);
            jpaEventOutboxRepository.saveAndFlush(jpaEvent);
        } catch (Exception exception) {
            log.error("Unable to publish application event {}", jpaEvent, exception);
            jpaEvent.setStatus(EventStatus.FAILED);
        }
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    @Transactional
    public void cleanUpEvents() {
        List<JpaEvent> processedEvents = jpaEventOutboxRepository.findByStatus(EventStatus.PROCESSED);
        jpaEventOutboxRepository.deleteAll(processedEvents);
    }
}
