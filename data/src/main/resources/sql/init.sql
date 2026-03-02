
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;

CREATE TABLE addresses (
    id                  BIGINT PRIMARY KEY,
    street              VARCHAR(255) NOT NULL,
    city                VARCHAR(100) NOT NULL,
    state               VARCHAR(50)  NOT NULL,
    zip_code            VARCHAR(10)  NOT NULL,
    is_apartment        BOOLEAN      NOT NULL,
);

CREATE TABLE users (
    id                   BIGINT PRIMARY KEY,

    first_name           VARCHAR(100) NOT NULL,
    last_name            VARCHAR(100) NOT NULL,
    email                VARCHAR(255) NOT NULL,
    phone                VARCHAR(25)  NOT NULL,

    primary_address_id   BIGINT NOT NULL,
    secondary_address_id BIGINT,

    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by           BIGINT,

    -- Foreign keys
    CONSTRAINT fk_user_primary_address
        FOREIGN KEY (primary_address_id)
        REFERENCES addresses(id),

    CONSTRAINT fk_user_secondary_address
        FOREIGN KEY (secondary_address_id)
        REFERENCES addresses(id),

);