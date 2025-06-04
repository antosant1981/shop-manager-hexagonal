package org.shop.manager.hexagonal.application.usecase;

import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.domain.EventPublisher;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.domain.TransactionService;
import org.shop.manager.hexagonal.vocabulary.*;

public class UpdateProductUseCase implements UpdateProduct {

    private final TransactionService transactionService;
    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    public UpdateProductUseCase(TransactionService transactionService,
                                ProductRepository productRepository,
                                EventPublisher eventPublisher) {
        this.transactionService = transactionService;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(Command command, Presenter presenter) {
        transactionService.doInTransaction(() -> executeInternal(command, presenter));
    }

    private void executeInternal(Command command, Presenter presenter) {

        productRepository.findBySerialNumber(command.serialNumber().serialNumber())
                .ifPresentOrElse(product -> {
                    updateAndSaveProduct(product,
                            command.barcode(),
                            command.name(),
                            command.description(),
                            command.price(),
                            command.status());
                    presenter.successAfterUpdate(product);
                }, () -> {
                    var product = createSaveAndGetProduct(command.serialNumber(),
                            command.barcode(),
                            command.name(),
                            command.description(),
                            command.price(),
                            command.status());
                    presenter.successAfterCreation(product);
                });
    }

    private void updateAndSaveProduct(Product product,
                                      BarCode barCode,
                                      ProductName productName,
                                      String description,
                                      Price price,
                                      Status status) {
        product.update(barCode, productName, description, price, status, eventPublisher);
        saveProduct(product);
    }

    private Product createSaveAndGetProduct(SerialNumber serialNumber,
                                            BarCode barCode,
                                            ProductName productName,
                                            String description,
                                            Price price,
                                            Status status) {
        var product = productToStore(serialNumber, barCode, productName, description, price, status);
        saveProduct(product);
        return product;
    }

    private Product productToStore(SerialNumber serialNumber,
                                   BarCode barCode,
                                   ProductName productName,
                                   String description,
                                   Price price,
                                   Status status) {
        return Product.create(serialNumber,
                barCode,
                productName,
                description,
                price,
                status,
                eventPublisher);
    }

    private void saveProduct(Product product) {
        productRepository.save(product);
    }
}
