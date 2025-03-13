package org.shop.manager.hexagonal.application.api;

import org.shop.manager.hexagonal.vocabulary.*;

public record QueryData(Identifier identifier,
                        SerialNumber serialNumber,
                        BarCode barCode,
                        Price price,
                        ProductName name,
                        String description,
                        Status status)  {}