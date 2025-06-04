package org.shop.manager.hexagonal.adapters.secondary.publisher;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEventOutboxRepository extends JpaRepository<JpaEvent, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<JpaEvent> findByStatus(EventStatus status);
}
