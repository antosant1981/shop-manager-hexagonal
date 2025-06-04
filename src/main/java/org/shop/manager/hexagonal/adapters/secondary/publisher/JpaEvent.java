package org.shop.manager.hexagonal.adapters.secondary.publisher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.shop.manager.hexagonal.vocabulary.Event;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "event")
@Setter
@Getter
public class JpaEvent {
    @Id
    private String id;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "event_data")
    private Event data;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    public JpaEvent() {
        this.id = UUID.randomUUID().toString();
    }
}
