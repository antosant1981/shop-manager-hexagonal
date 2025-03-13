package org.shop.manager.hexagonal.adapters.primary.rest.presenter;

import org.shop.manager.hexagonal.adapters.primary.rest.resource.ProductResponseResource;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateProductPresenter implements UpdateProduct.Presenter {
    private ResponseEntity<ProductResponseResource> response;

    @Override
    public void successAfterCreation(Product product) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseResource.fromProduct(product));
    }

    @Override
    public void successAfterUpdate(Product product) {
        response = ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<ProductResponseResource> toResponseEntity() {
        return response;
    }
}
