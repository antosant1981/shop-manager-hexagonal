package org.shop.manager.hexagonal.application.context;

import org.shop.manager.hexagonal.adapters.secondary.inmemory.InMemoryProductRepository;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.api.QueryData;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.vocabulary.Event;

import java.util.List;
import java.util.Optional;

public class ProductTestContext {

    private final ApplicationContext applicationContext = new ApplicationContext();

    public TestCreateProductPresenter createProduct(CreateProduct.Command command) {

        var presenter = new TestCreateProductPresenter();
        applicationContext.createProduct.execute(command, presenter);
        return presenter;
    }

    public TestUpdateProductPresenter updateProduct(UpdateProduct.Command command) {

        var presenter = new TestUpdateProductPresenter();
        applicationContext.updateProduct.execute(command, presenter);
        return presenter;
    }

    public TestDeleteProductPresenter deleteProduct(DeleteProduct.Command command) {
        var presenter = new TestDeleteProductPresenter();
        applicationContext.deleteProduct.execute(command, presenter);
        return presenter;
    }

    public InMemoryProductRepository productRepository() {
        return applicationContext.productRepository;
    }

    public Optional<Event> eventPublished() {
        return applicationContext.eventPublisher.getEvent();
    }

    public List<QueryData> findAllProducts() {
        return applicationContext.findProduct.findAll();
    }

    public Optional<QueryData> findProductBySerialNumber(String serialNumber) {
        return applicationContext.findProduct.findBySerialNumber(serialNumber);
    }
}
