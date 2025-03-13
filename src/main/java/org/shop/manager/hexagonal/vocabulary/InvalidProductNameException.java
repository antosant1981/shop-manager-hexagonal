package org.shop.manager.hexagonal.vocabulary;

public class InvalidProductNameException extends RuntimeException {

    public InvalidProductNameException(String message) {
        super(message);
    }
}
