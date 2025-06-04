package org.shop.manager.hexagonal.domain;

import org.shop.manager.hexagonal.vocabulary.Event;

public interface EventPublisher {
    void publish(Event event);
}
