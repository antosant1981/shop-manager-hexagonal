package org.shop.manager.hexagonal.adapters.primary.rest.presenter;

import org.shop.manager.hexagonal.adapters.primary.rest.resource.ProductResponseResource;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateProductPresenter implements CreateProduct.Presenter {
    private ResponseEntity<ProductResponseResource> response;

    @Override
    public void success(Product product) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseResource.fromProduct(product));
    }

    @Override
    public void conflict() {
        response = ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    public ResponseEntity<ProductResponseResource> toResponseEntity() {
        return response;
    }
}
