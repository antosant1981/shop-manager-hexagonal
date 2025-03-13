package org.shop.manager.hexagonal.adapters.secondary.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<JpaProduct, String>, PagingAndSortingRepository<JpaProduct, String>, JpaSpecificationExecutor<JpaProduct> {
    Optional<JpaProduct> findBySerialNumber(String serialNumber);
}
