package org.shop.manager.hexagonal.vocabulary;

public class PriceNotValidException extends RuntimeException {
    public PriceNotValidException(String message) {
        super(message);
    }
}
