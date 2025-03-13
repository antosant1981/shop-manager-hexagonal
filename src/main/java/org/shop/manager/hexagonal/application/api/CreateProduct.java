package org.shop.manager.hexagonal.application.api;

import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.vocabulary.*;

public interface CreateProduct {
    void execute(Command command, Presenter presenter);

    interface Presenter {
        void success(Product product);
        void conflict();
    }

    record Command(SerialNumber serialNumber,
                   BarCode barcode,
                   ProductName name,
                   String description,
                   Price price,
                   Status status) {}
}