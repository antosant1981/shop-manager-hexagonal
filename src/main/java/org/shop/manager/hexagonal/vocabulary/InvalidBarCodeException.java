package org.shop.manager.hexagonal.vocabulary;

public class InvalidBarCodeException extends RuntimeException {
    public InvalidBarCodeException(String message) {
        super(message);
    }
}
