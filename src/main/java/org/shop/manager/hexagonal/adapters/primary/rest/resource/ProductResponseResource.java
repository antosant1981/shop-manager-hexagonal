package org.shop.manager.hexagonal.adapters.primary.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.shop.manager.hexagonal.application.api.QueryData;
import org.shop.manager.hexagonal.domain.Product;

public record ProductResponseResource(@JsonProperty(value = "serial_number", required = true) String serialNumber,
                                      @JsonProperty(value = "bar_code", required = true) String barCode,
                                      @JsonProperty(value = "name", required = true) String name,
                                      @JsonProperty(value = "description") String description,
                                      @JsonProperty(value = "price", required = true) Double price,
                                      @JsonProperty(value = "status", required = true) String status) {

    public static ProductResponseResource fromProduct(Product product) {
        return new ProductResponseResource(product.toSnapshot().serialNumber().serialNumber(),
                product.toSnapshot().barCode().barCode(),
                product.toSnapshot().name().productName(),
                product.toSnapshot().description(),
                product.toSnapshot().price().price(),
                product.toSnapshot().status().name());
    }

    public static ProductResponseResource fromQueryData(QueryData queryData) {
        return new ProductResponseResource(queryData.serialNumber().serialNumber(),
                queryData.barCode().barCode(),
                queryData.name().productName(),
                queryData.description(),
                queryData.price().price(),
                queryData.status().name());
    }
}
