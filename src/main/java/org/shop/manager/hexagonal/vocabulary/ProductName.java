package org.shop.manager.hexagonal.vocabulary;

import java.util.Objects;

public record ProductName(String productName) {
    public ProductName {
        if(Objects.isNull(productName) || productName.isEmpty()) {
            throw new InvalidProductNameException("The Product name provided is not valid");
        }
    }
}
