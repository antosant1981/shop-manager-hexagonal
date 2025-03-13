package org.shop.manager.hexagonal.application.context;

import lombok.Getter;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.domain.Product;

@Getter
public class TestUpdateProductPresenter implements UpdateProduct.Presenter {
    private boolean successAfterCreation;
    private boolean successAfterUpdate;


    @Override
    public void successAfterCreation(Product product) {
        successAfterCreation = true;
    }

    @Override
    public void successAfterUpdate(Product product) {
        successAfterUpdate = true;
    }
}
