package org.shop.manager.hexagonal.domain;

import java.util.function.Supplier;

public interface TransactionService {
    void doInTransaction(Runnable action);
    <T> T doInTransactionWithResponse(Supplier<T> action);
}
