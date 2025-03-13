CREATE TABLE product
(
    id                  VARCHAR(255),
    serial_number       VARCHAR(36) NOT NULL,
    bar_code            VARCHAR(32) NOT null,
    name                VARCHAR(255) NOT NULL,
    description         VARCHAR(500),
    price               DECIMAL(10,3) NOT NULL,
    status              VARCHAR(50) NOT NULL DEFAULT 'NOT_AVAILABLE',
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP,
    CONSTRAINT pk_product PRIMARY KEY (id)
);