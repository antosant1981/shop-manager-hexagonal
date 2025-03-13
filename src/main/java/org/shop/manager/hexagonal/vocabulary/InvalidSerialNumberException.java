package org.shop.manager.hexagonal.vocabulary;

public class InvalidSerialNumberException extends RuntimeException {
    public InvalidSerialNumberException(String message) {
        super(message);
    }
}
