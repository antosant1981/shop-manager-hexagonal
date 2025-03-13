package org.shop.manager.hexagonal.application.context;

import lombok.Getter;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.domain.Product;

@Getter
public class TestCreateProductPresenter implements CreateProduct.Presenter {
    private boolean success;
    private boolean conflict;

    @Override
    public void success(Product product) {
        success = true;
    }

    @Override
    public void conflict() {
        conflict = true;
    }
}
