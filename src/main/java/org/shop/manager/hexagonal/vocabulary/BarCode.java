package org.shop.manager.hexagonal.vocabulary;

import java.util.Objects;

public record BarCode(String barCode) {
    public BarCode {

        if(Objects.isNull(barCode) || barCode.isBlank()) {
            throw new InvalidBarCodeException("The barcode provided is not valid");
        }

        if(barCode.length() != 32) {
            throw new InvalidBarCodeException("The barcode provided is not valid");
        }
    }
}
