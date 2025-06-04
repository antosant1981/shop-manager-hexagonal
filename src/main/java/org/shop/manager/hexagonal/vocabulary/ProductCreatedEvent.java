package org.shop.manager.hexagonal.vocabulary;

public record ProductCreatedEvent(String id,
                                  String serialNumber,
                                  String barCode,
                                  String productName) implements Event {
}
