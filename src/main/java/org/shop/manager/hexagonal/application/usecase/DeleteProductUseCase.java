package org.shop.manager.hexagonal.application.usecase;

import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.domain.EventPublisher;
import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.vocabulary.ProductDeletedEvent;

public class DeleteProductUseCase implements DeleteProduct {

    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    public DeleteProductUseCase(ProductRepository productRepository,
                                EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(Command command, Presenter presenter) {
        productRepository.findBySerialNumber(command.serialNumber().serialNumber())
                .ifPresentOrElse(product -> {
                    deleteProduct(product);
                    publishEvent(product);
                    presenter.success(product);
                }, presenter::notFound);
    }

    private void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    private void publishEvent(Product product) {
        var productSnapshot = product.toSnapshot();
        eventPublisher.publish(new ProductDeletedEvent(
                productSnapshot.id().asString(),
                productSnapshot.serialNumber().serialNumber(),
                productSnapshot.barCode().barCode(),
                productSnapshot.name().productName()));
    }
}
