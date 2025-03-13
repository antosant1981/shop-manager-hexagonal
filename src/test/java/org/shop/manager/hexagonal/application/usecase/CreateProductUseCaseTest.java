package org.shop.manager.hexagonal.application.usecase;

import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.application.api.CreateProduct;
import org.shop.manager.hexagonal.application.context.ProductTestContext;
import org.shop.manager.hexagonal.domain.ProductMother;
import org.shop.manager.hexagonal.vocabulary.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateProductUseCaseTest {

    private final ProductTestContext productTestContext = new ProductTestContext();

    @Test
    void createNonExistingProductWithSuccess() {
        var feedback = productTestContext.createProduct(new CreateProduct.Command(
                new SerialNumber(UUID.randomUUID().toString()),
                new BarCode("A_BARCODE_32_CHARACTERS_LONG_123"),
                new ProductName("Women shoes"),
                "some short description",
                new Price(32.70),
                Status.AVAILABLE));

        assertThat(feedback.isSuccess()).isTrue();
    }

    @Test
    void createExistingProductWithConflict() {
        var aListOfValidProduct = ProductMother.aListOfThreeValidProducts();
        productTestContext.productRepository().setup(aListOfValidProduct);

        var anExistingProductSerialNumber = aListOfValidProduct.getFirst().toSnapshot().serialNumber();

        var feedback = productTestContext.createProduct(new CreateProduct.Command(
                anExistingProductSerialNumber,
                new BarCode("A_BARCODE_32_CHARACTERS_LONG_123"),
                new ProductName("Women shoes"),
                "some short description",
                new Price(32.70),
                Status.AVAILABLE));

        assertThat(feedback.isConflict()).isTrue();
    }
}
