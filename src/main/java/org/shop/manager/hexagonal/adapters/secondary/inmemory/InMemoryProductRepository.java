package org.shop.manager.hexagonal.adapters.secondary.inmemory;

import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductData;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.vocabulary.Identifier;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    Map<Identifier, ProductData> productsMap = new HashMap<>();

    public void setup(List<Product> products) {
        products.forEach(product -> {
            var productSnapshot = product.toSnapshot();
            productsMap.put(productSnapshot.id(), productSnapshot);
        });
    }

    @Override
    public void save(Product product) {
        var productSnapshot = product.toSnapshot();
        productsMap.put(productSnapshot.id(), productSnapshot);
    }

    @Override
    public List<Product> findAll() {
        return productsMap.values().stream().map(Product::fromSnapshot).toList();
    }

    @Override
    public Optional<Product> findBySerialNumber(String serialNumber) {
        return productsMap.values()
                .stream()
                .filter(v -> serialNumber.equals(v.serialNumber().serialNumber()))
                        .map(Product::fromSnapshot)
                .findFirst();
    }

    @Override
    public void delete(Product product) {
        var productSnapshot = product.toSnapshot();
        productsMap.values().remove(productSnapshot);
    }
}
