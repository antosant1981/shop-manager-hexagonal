package org.shop.manager.hexagonal.application.query;

import org.shop.manager.hexagonal.application.api.FindProduct;
import org.shop.manager.hexagonal.application.api.QueryData;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;

import java.util.List;
import java.util.Optional;

public class FindProductQuery implements FindProduct {

    private final ProductRepository productRepository;

    public FindProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<QueryData> findAll() {
        return productRepository.findAll().stream().map(this::toQueryData).toList();
    }

    @Override
    public Optional<QueryData> findBySerialNumber(String serialNumber) {
        return productRepository.findBySerialNumber(serialNumber).map(this::toQueryData);
    }

    private QueryData toQueryData(Product product) {

        var productSnapshot = product.toSnapshot();

        return new QueryData(productSnapshot.id(),
                productSnapshot.serialNumber(),
                productSnapshot.barCode(),
                productSnapshot.price(),
                productSnapshot.name(),
                productSnapshot.description(),
                productSnapshot.status());
    }
}
