package org.shop.manager.hexagonal.adapters.secondary.postgres;

import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;

import java.util.List;
import java.util.Optional;

public class PostgresProductRepository implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    public PostgresProductRepository(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public void save(Product product) {
        jpaProductRepository.save(JpaProduct.of(product.toSnapshot()));
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(JpaProduct::toProductData)
                .map(Product::fromSnapshot)
                .toList();
    }

    @Override
    public Optional<Product> findBySerialNumber(String serialNumber) {
        return jpaProductRepository.findBySerialNumber(serialNumber)
                .map(JpaProduct::toProductData)
                .map(Product::fromSnapshot);
    }

    @Override
    public void delete(Product product) {
        jpaProductRepository.delete(JpaProduct.of(product.toSnapshot()));
    }
}
