package org.shop.manager.hexagonal.application.context;

import lombok.Getter;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.domain.Product;

@Getter
public class TestDeleteProductPresenter implements DeleteProduct.Presenter {
    private boolean success;
    private boolean notFound;

    @Override
    public void success(Product product) {
        success = true;
    }

    @Override
    public void notFound() {
        notFound = true;
    }
}
