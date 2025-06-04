package org.shop.manager.hexagonal.application.usecase;

import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.application.api.UpdateProduct;
import org.shop.manager.hexagonal.application.context.ProductTestContext;
import org.shop.manager.hexagonal.domain.ProductMother;
import org.shop.manager.hexagonal.vocabulary.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateProductUseCaseTest {

    private final ProductTestContext productTestContext = new ProductTestContext();

    @Test
    void updateExistingProductWithSuccess() {

        var aListOfValidProduct = ProductMother.aListOfThreeValidProducts();
        productTestContext.productRepository().setup(aListOfValidProduct);

        var anExistingProductSerialNumber = aListOfValidProduct.getFirst().toSnapshot().serialNumber();
        var aNewBarCode = new BarCode("123456789098765_mnbhytre_MANSHYT");
        var aNewPrice = new Price(123.09);
        var aNewName = new ProductName("Another name");

        var feedback = productTestContext.updateProduct(new UpdateProduct.Command(
                anExistingProductSerialNumber,
                aNewBarCode,
                aNewName,
                "Some kind of short description",
                aNewPrice,
                Status.IN_STOCK));

        assertThat(feedback.isSuccessAfterUpdate()).isTrue();
        var eventPublished = productTestContext.eventPublished();
        assertThat(eventPublished).isPresent();
        assertThat(eventPublished.get()).isExactlyInstanceOf(ProductUpdatedEvent.class);
    }

    @Test
    void updateNonExistingProductWithSuccess() {

        var aNewSerialNumber = new SerialNumber(UUID.randomUUID().toString());
        var aNewBarCode = new BarCode("123456789098765_mnbhytre_MANSHYT");
        var aNewPrice = new Price(123.09);
        var aNewName = new ProductName("A name");

        var feedback = productTestContext.updateProduct(new UpdateProduct.Command(
                aNewSerialNumber,
                aNewBarCode,
                aNewName,
                "Some kind of short description",
                aNewPrice,
                Status.IN_STOCK));

        assertThat(feedback.isSuccessAfterCreation()).isTrue();
        var eventPublished = productTestContext.eventPublished();
        assertThat(eventPublished).isPresent();
        assertThat(eventPublished.get()).isExactlyInstanceOf(ProductCreatedEvent.class);
    }
}
