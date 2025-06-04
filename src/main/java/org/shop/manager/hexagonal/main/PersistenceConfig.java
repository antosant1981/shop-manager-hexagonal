package org.shop.manager.hexagonal.main;

import org.shop.manager.hexagonal.adapters.secondary.postgres.JpaProductRepository;
import org.shop.manager.hexagonal.adapters.secondary.postgres.PostgresProductRepository;
import org.shop.manager.hexagonal.adapters.secondary.postgres.SpringTransactionService;
import org.shop.manager.hexagonal.domain.ProductRepository;
import org.shop.manager.hexagonal.domain.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.shop.manager.hexagonal.adapters.secondary.postgres",
        "org.shop.manager.hexagonal.adapters.secondary.publisher"})
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    public TransactionService transactionService(TransactionTemplate transactionTemplate) {
        return new SpringTransactionService(transactionTemplate);
    }

    @Bean
    public ProductRepository productRepository(JpaProductRepository jpaProductRepository) {
        return new PostgresProductRepository(jpaProductRepository);
    }
}
