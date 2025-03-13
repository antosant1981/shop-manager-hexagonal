package org.shop.manager.hexagonal.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);
    List<Product> findAll();
    Optional<Product> findBySerialNumber(String serialNumber);
    void delete(Product product);
}
