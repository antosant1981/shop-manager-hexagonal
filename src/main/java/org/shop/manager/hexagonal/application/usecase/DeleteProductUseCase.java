package org.shop.manager.hexagonal.application.usecase;

import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;

public class DeleteProductUseCase implements DeleteProduct {

    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(Command command, Presenter presenter) {
        productRepository.findBySerialNumber(command.serialNumber().serialNumber())
                .ifPresentOrElse(product -> {
                    deleteProduct(product);
                    presenter.success(product);
                    }, presenter::notFound);
    }

    private void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
