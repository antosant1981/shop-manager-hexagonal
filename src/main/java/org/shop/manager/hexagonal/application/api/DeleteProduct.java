package org.shop.manager.hexagonal.application.api;

import org.shop.manager.hexagonal.domain.Product;
import org.shop.manager.hexagonal.vocabulary.SerialNumber;

public interface DeleteProduct {

    void execute(Command command, Presenter presenter);

    interface Presenter {
        void success(Product product);
        void notFound();
    }

    record Command(SerialNumber serialNumber){}
}
