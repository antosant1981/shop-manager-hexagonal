package org.shop.manager.hexagonal.adapters.secondary.postgres;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.shop.manager.hexagonal.domain.ProductMother;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostgresProductRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostgresProductRepository productRepository;

    @Test
    void saveProduct() {

     var aValidProduct = ProductMother.aValidProduct().build();
     productRepository.save(aValidProduct);

     var productSnapshot = aValidProduct.toSnapshot();

     var actual = entityManager.find(JpaProduct.class, aValidProduct.id().asString());

     assertThat(actual).isNotNull();
     assertThat(actual.getBarCode()).isEqualTo(productSnapshot.barCode().barCode());
     assertThat(actual.getName()).isEqualTo(productSnapshot.name().productName());
     assertThat(actual.getCreatedAt()).isEqualTo(productSnapshot.createdAt().toInstant());
     assertThat(actual.getUpdatedAt()).isEqualTo(productSnapshot.updatedAt().toInstant());
    }


    @Test
    void findAll() {
        var aListOfProducts = ProductMother.aListOfThreeValidProducts();

        aListOfProducts.forEach(product -> entityManager.persist(JpaProduct.of(product.toSnapshot())));

        var products = productRepository.findAll();

        assertThat(products).isNotEmpty().hasSize(3);
    }

    @Test void findAllWithNoResult() {

        var products = productRepository.findAll();

        assertThat(products).isEmpty();
    }

    @Test
    void findBySerialNumberWithResult() {
        var aValidProduct = ProductMother.aValidProduct().build();
        entityManager.persist(JpaProduct.of(aValidProduct.toSnapshot()));

        var productToSearch = productRepository.findBySerialNumber(
                aValidProduct.toSnapshot().serialNumber().serialNumber());

        assertThat(productToSearch).isPresent();
    }

    @Test
    void findBySerialNumberWithNoResult() {
        var productToSearch = productRepository.findBySerialNumber("any-serial-number");

        assertThat(productToSearch).isNotPresent();
    }

    @Test
    void deleteProduct() {
        var aValidProduct = ProductMother.aValidProduct().build();
        entityManager.persist(JpaProduct.of(aValidProduct.toSnapshot()));

        productRepository.delete(aValidProduct);

        var actual = entityManager.find(JpaProduct.class, aValidProduct.id().asString());
        assertThat(actual).isNull();
    }
}
