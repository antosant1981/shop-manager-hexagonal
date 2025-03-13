package org.shop.manager.hexagonal.vocabulary;


import java.util.Objects;
import java.util.UUID;

public record SerialNumber(String serialNumber) {
    public SerialNumber {
        if(Objects.isNull(serialNumber) || serialNumber.isBlank()) {
            throw new InvalidSerialNumberException("The serial number provided is not valid");
        }

        try {
            UUID.fromString(serialNumber);
        } catch (IllegalArgumentException e) {
            throw new InvalidSerialNumberException("The serial number provided is not valid");
        }
    }
}
