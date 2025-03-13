package org.shop.manager.hexagonal.domain;

import lombok.Builder;
import org.shop.manager.hexagonal.vocabulary.*;

import java.time.OffsetDateTime;

@Builder
public class Product {

    @Builder.Default
    private final Identifier id = Identifier.nextVal();

    private SerialNumber serialNumber;
    private BarCode barCode;
    private ProductName name;
    private String description;
    private Price price;
    private Status status;

    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public ProductData toSnapshot() {
        return  new ProductData(id,
                serialNumber,
                barCode,
                name,
                description,
                price,
                status,
                createdAt,
                updatedAt);
    }

    public static Product create(SerialNumber serialNumber,
                           BarCode barCode,
                           ProductName name,
                           String description,
                           Price price,
                           Status status) {
        return Product.builder()
                .id(Identifier.nextVal())
                .serialNumber(serialNumber)
                .barCode(barCode)
                .name(name)
                .description(description)
                .price(price)
                .status(status).build();
    }

    public void update(BarCode barCode,
                       ProductName name,
                       String description,
                       Price price,
                       Status status) {
        this.barCode = barCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public static Product fromSnapshot(ProductData snapshot) {
        var product = Product.builder();
        product.id(snapshot.id())
                .serialNumber(snapshot.serialNumber())
                .barCode(snapshot.barCode())
                .name(snapshot.name())
                .description(snapshot.description())
                .price(snapshot.price())
                .status(snapshot.status())
                .createdAt(snapshot.createdAt())
                .updatedAt(snapshot.updatedAt());
        return product.build();
    }

    public Identifier id() {
        return id;
    }
}
