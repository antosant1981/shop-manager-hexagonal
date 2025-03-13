package org.shop.manager.hexagonal.adapters.secondary.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.shop.manager.hexagonal.domain.ProductData;
import org.shop.manager.hexagonal.vocabulary.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Entity
@Table(name = "product")
@Getter
@Setter
public class JpaProduct {
    @Id
    private String id;
    private String serialNumber;
    private String barCode;
    private String name;
    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;


    public static JpaProduct of(ProductData productData) {
        var jpaProduct = new JpaProduct();

        jpaProduct.setId(productData.id().asString());
        jpaProduct.setSerialNumber(Optional.of(productData.serialNumber().serialNumber()).orElse(null));
        jpaProduct.setBarCode(Optional.of(productData.barCode().barCode()).orElse(null));
        jpaProduct.setName(Optional.of(productData.name().productName()).orElse(null));
        jpaProduct.setDescription(Optional.of(productData.description()).orElse(null));
        jpaProduct.setPrice(Optional.of(BigDecimal.valueOf(productData.price().price())).orElse(null));
        jpaProduct.setStatus(Optional.of(productData.status()).orElse(null));
        jpaProduct.setCreatedAt(Optional.of(productData.createdAt()).map(OffsetDateTime::toInstant).orElse(null));
        jpaProduct.setUpdatedAt(Optional.of(productData.updatedAt()).map(OffsetDateTime::toInstant).orElse(null));

        return jpaProduct;
    }

    public static ProductData toProductData(JpaProduct jpaProduct) {
        return new ProductData(Identifier.fromString(jpaProduct.id),
                new SerialNumber(jpaProduct.serialNumber),
                new BarCode(jpaProduct.barCode),
                new ProductName(jpaProduct.name),
                jpaProduct.description,
                new Price(jpaProduct.price.doubleValue()),
                Status.valueOf(jpaProduct.status.name()),
                jpaProduct.createdAt.atOffset(ZoneOffset.UTC),
                jpaProduct.createdAt.atOffset(ZoneOffset.UTC));
    }
}
