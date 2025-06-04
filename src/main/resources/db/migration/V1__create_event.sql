CREATE TABLE event
(
    id         VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    event_data JSONB,
    status     VARCHAR(255),
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE INDEX idx_event_status on event(status);