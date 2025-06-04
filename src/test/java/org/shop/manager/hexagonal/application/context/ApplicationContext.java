package org.shop.manager.hexagonal.application.context;

import org.shop.manager.hexagonal.adapters.secondary.inmemory.InMemoryEventPublisher;
import org.shop.manager.hexagonal.adapters.secondary.inmemory.InMemoryProductRepository;
import org.shop.manager.hexagonal.adapters.secondary.inmemory.NoOpTransactionService;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.api.FindProduct;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.application.query.FindProductQuery;
import org.shop.manager.hexagonal.application.usecase.CreateProductUseCase;
import org.shop.manager.hexagonal.application.usecase.DeleteProductUseCase;
import org.shop.manager.hexagonal.application.usecase.UpdateProductUseCase;

public class ApplicationContext {

    final NoOpTransactionService transactionService = new NoOpTransactionService();

    final InMemoryProductRepository productRepository = new InMemoryProductRepository();
    final InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();

    final CreateProduct createProduct = new CreateProductUseCase(transactionService, productRepository, eventPublisher);
    final UpdateProduct updateProduct = new UpdateProductUseCase(transactionService, productRepository, eventPublisher);
    final FindProduct findProduct = new FindProductQuery(productRepository);
    final DeleteProduct deleteProduct = new DeleteProductUseCase(productRepository, eventPublisher);
}
