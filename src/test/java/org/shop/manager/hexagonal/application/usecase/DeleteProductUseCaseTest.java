package org.shop.manager.hexagonal.application.usecase;

import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.application.api.DeleteProduct;
import org.shop.manager.hexagonal.application.context.ProductTestContext;
import org.shop.manager.hexagonal.domain.ProductMother;
import org.shop.manager.hexagonal.vocabulary.ProductDeletedEvent;
import org.shop.manager.hexagonal.vocabulary.SerialNumber;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteProductUseCaseTest {

    private final ProductTestContext productTestContext = new ProductTestContext();

    @Test
    void deleteExistingProductWithSuccess() {
        var aListOfValidProduct = ProductMother.aListOfThreeValidProducts();
        productTestContext.productRepository().setup(ProductMother.aListOfThreeValidProducts());

        var anExistingProductSerialNumber = aListOfValidProduct.getFirst().toSnapshot().serialNumber();

        var feedback = productTestContext.deleteProduct(new DeleteProduct.Command(anExistingProductSerialNumber));

        assertThat(feedback.isSuccess()).isTrue();
        var eventPublished = productTestContext.eventPublished();
        assertThat(eventPublished).isPresent();
        assertThat(eventPublished.get()).isExactlyInstanceOf(ProductDeletedEvent.class);
    }

    @Test
    void deleteNonExistingProductWithSuccess() {
        var unexistingSerialNumber = new SerialNumber(UUID.randomUUID().toString());

        var feedback = productTestContext.deleteProduct(new DeleteProduct.Command(unexistingSerialNumber));

        assertThat(feedback.isNotFound()).isTrue();
        var eventPublished = productTestContext.eventPublished();
        assertThat(eventPublished).isNotPresent();
    }
}
