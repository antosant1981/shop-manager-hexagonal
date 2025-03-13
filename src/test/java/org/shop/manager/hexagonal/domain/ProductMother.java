package org.shop.manager.hexagonal.domain;

import org.shop.manager.hexagonal.vocabulary.*;

import java.util.List;

public class ProductMother {

    public static Product.ProductBuilder aValidProduct() {
        return Product.builder()
                .serialNumber(new SerialNumber("e3566411-8ae9-404a-8917-924973a0446a"))
                .barCode(new BarCode("1234567890_abcdeflopanfrtan_NMLP"))
                .price(new Price(222.09))
                .name(new ProductName("Electric drill"))
                .description("This is a brief description about the electric drill")
                .status(Status.AVAILABLE);
    }

    public static List<Product> aListOfThreeValidProducts() {
        return List.of(aValidProduct().build(),
                aValidProduct().build(),
                aValidProduct().build());
    }
}
