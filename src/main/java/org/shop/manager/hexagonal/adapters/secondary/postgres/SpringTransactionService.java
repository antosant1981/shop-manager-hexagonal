package org.shop.manager.hexagonal.adapters.secondary.postgres;

import org.shop.manager.hexagonal.domain.TransactionService;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

public class SpringTransactionService implements TransactionService {

    private final TransactionTemplate transactionTemplate;

    public SpringTransactionService(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void doInTransaction(Runnable action) {
        transactionTemplate.executeWithoutResult(transactionStatus -> action.run());
    }

    @Override
    public <T> T doInTransactionWithResponse(Supplier<T> action) {
        return transactionTemplate.execute(status -> action.get());
    }
}
