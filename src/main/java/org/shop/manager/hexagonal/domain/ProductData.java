package org.shop.manager.hexagonal.domain;

import org.shop.manager.hexagonal.vocabulary.*;

import java.time.OffsetDateTime;

public record ProductData(Identifier id,
                          SerialNumber serialNumber,
                          BarCode barCode,
                          ProductName name,
                          String description,
                          Price price,
                          Status status,
                          OffsetDateTime createdAt,
                          OffsetDateTime updatedAt) { }