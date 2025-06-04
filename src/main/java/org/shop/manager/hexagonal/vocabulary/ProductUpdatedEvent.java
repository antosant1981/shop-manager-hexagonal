package org.shop.manager.hexagonal.vocabulary;

public record ProductUpdatedEvent(String id,
                                  String serialNumber,
                                  String barCode,
                                  String productName) implements Event {
}
