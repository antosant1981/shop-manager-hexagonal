package org.shop.manager.hexagonal.vocabulary;

import java.util.Objects;

public record Price(Double price) {
    public Price {
        if(Objects.isNull(price) || price < 0) {
            throw new PriceNotValidException("The provided price is not valid");
        }
    }
}
