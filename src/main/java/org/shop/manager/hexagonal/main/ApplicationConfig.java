package org.shop.manager.hexagonal.main;

import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.api.FindProduct;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.application.query.FindProductQuery;
import org.shop.manager.hexagonal.application.usecase.CreateProductUseCase;
import org.shop.manager.hexagonal.application.usecase.DeleteProductUseCase;
import org.shop.manager.hexagonal.application.usecase.UpdateProductUseCase;
import org.shop.manager.hexagonal.domain.EventPublisher;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.domain.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CreateProduct createProduct(TransactionService transactionService,
                                       ProductRepository productRepository,
                                       EventPublisher eventPublisher) {
        return new CreateProductUseCase(transactionService, productRepository, eventPublisher);
    }

    @Bean
    public UpdateProduct updateProduct(TransactionService transactionService,
                                       ProductRepository productRepository,
                                       EventPublisher eventPublisher) {
        return new UpdateProductUseCase(transactionService, productRepository, eventPublisher);
    }

    @Bean
    public FindProduct findProduct(ProductRepository productRepository) {
        return new FindProductQuery(productRepository);
    }

    @Bean
    public DeleteProduct deleteProduct(ProductRepository productRepository, EventPublisher eventPublisher) {
        return new DeleteProductUseCase(productRepository, eventPublisher);
    }
}
