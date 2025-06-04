package org.shop.manager.hexagonal.vocabulary;

public record ProductDeletedEvent(String id,
                                  String serialNumber,
                                  String barCode,
                                  String productName) implements Event {
}
