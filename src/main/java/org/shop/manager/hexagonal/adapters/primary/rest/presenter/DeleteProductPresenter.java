package org.shop.manager.hexagonal.adapters.primary.rest.presenter;

import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteProductPresenter implements DeleteProduct.Presenter {
    private ResponseEntity<String> response;

    @Override
    public void success(Product product) {
        response = ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }

    @Override
    public void notFound() {
        response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<String> toResponseEntity() {
        return response;
    }
}
