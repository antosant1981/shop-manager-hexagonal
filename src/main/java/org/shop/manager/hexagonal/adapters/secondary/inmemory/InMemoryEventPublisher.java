package org.shop.manager.hexagonal.adapters.secondary.inmemory;

import org.shop.manager.hexagonal.domain.EventPublisher;
import org.shop.manager.hexagonal.vocabulary.Event;

import java.util.Optional;

public class InMemoryEventPublisher implements EventPublisher {
    private Event event;

    public Optional<Event> getEvent() {
        return Optional.ofNullable(event);
    }

    @Override
    public void publish(Event event) {
        this.event = event;
    }
}
