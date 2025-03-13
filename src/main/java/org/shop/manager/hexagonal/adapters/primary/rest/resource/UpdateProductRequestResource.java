package org.shop.manager.hexagonal.adapters.primary.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProductRequestResource(@JsonProperty(value = "bar_code", required = true) String barCode,
                                           @JsonProperty(value = "name", required = true) String name,
                                           @JsonProperty(value = "description") String description,
                                           @JsonProperty(value = "price", required = true) Double price,
                                           @JsonProperty(value = "status", required = true) String status) {}
