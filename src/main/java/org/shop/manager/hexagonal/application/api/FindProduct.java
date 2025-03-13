package org.shop.manager.hexagonal.application.api;

import java.util.List;
import java.util.Optional;

public interface FindProduct {
    List<QueryData> findAll();
    Optional<QueryData> findBySerialNumber(String serialNumber);
}
