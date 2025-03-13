package org.shop.manager.hexagonal.application.usecase;

import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.domain.TransactionService;
import org.shop.manager.hexagonal.vocabulary.*;

public class CreateProductUseCase implements CreateProduct {

    private final TransactionService transactionService;
    private final ProductRepository productRepository;

    public CreateProductUseCase(TransactionService transactionService, ProductRepository productRepository) {
        this.transactionService = transactionService;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(Command command, Presenter presenter) {
        transactionService.doInTransaction(() -> executeInternal(command, presenter));
    }

    private void executeInternal(Command command, Presenter presenter) {
        productRepository.findBySerialNumber(command.serialNumber().serialNumber())
                .ifPresentOrElse(product -> presenter.conflict(), () -> {
                    var product = createSaveAndGetProduct(command.serialNumber(),
                            command.barcode(),
                            command.name(),
                            command.description(),
                            command.price(),
                            command.status());
                    presenter.success(product);
                });
    }

    private Product createSaveAndGetProduct(SerialNumber serialNumber,
                                            BarCode barCode,
                                            ProductName productName,
                                            String description,
                                            Price price,
                                            Status status) {
        var product = productToStore(serialNumber, barCode, productName, description, price, status);
        productRepository.save(product);
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
                status);
    }
}
