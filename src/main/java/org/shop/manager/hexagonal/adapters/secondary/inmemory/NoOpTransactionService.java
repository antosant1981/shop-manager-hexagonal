package org.shop.manager.hexagonal.adapters.secondary.inmemory;

import org.shop.manager.hexagonal.domain.TransactionService;

import java.util.function.Supplier;

public class NoOpTransactionService implements TransactionService {
    @Override
    public void doInTransaction(Runnable action) {
        action.run();
    }

    @Override
    public <T> T doInTransactionWithResponse(Supplier<T> action) {
        return action.get();
    }
}
