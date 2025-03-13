package org.shop.manager.hexagonal.application.query;

import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.application.context.ProductTestContext;
import org.shop.manager.hexagonal.domain.ProductMother;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class FindProductQueryTest {

    private final ProductTestContext productTestContext = new ProductTestContext();


    @Test
    void fetchAllProductReturnsEmptyWhenNoProductFound() {

        productTestContext.productRepository().setup(List.of());

        var result = productTestContext.findAllProducts();

        assertThat(result).isEmpty();
    }

    @Test
    void fetchAllProductReturnsThreeItemsWhenThreeProductsFound() {

        var threeProds = ProductMother.aListOfThreeValidProducts();
        productTestContext.productRepository().setup(threeProds);

        var result = productTestContext.findAllProducts();

        assertThat(result).isNotEmpty();
    }

    @Test
    void fetchProductBySerialNumberReturnsAResultWhenProductFound() {

        var threeProds = ProductMother.aListOfThreeValidProducts();
        productTestContext.productRepository().setup(threeProds);
        var anExistingProductSerialNumber = threeProds.getFirst().toSnapshot().serialNumber().serialNumber();

        var result = productTestContext.findProductBySerialNumber(anExistingProductSerialNumber);

        assertThat(result).isPresent();
    }

    @Test
    void fetchProductBySerialNumberReturnsEmptyWhenNoProductFound() {

        var result = productTestContext.findProductBySerialNumber("anySerialNumber");

        assertThat(result).isEmpty();
    }
}
