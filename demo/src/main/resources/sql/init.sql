CREATE TABLE "address" (
    id                  BIGINT PRIMARY KEY,
    street              VARCHAR(255) NOT NULL,
    city                VARCHAR(100) NOT NULL,
    state               VARCHAR(50)  NOT NULL,
    zip_code            VARCHAR(10)  NOT NULL,
    is_apartment        BOOLEAN,

    created_at           TIMESTAMP NOT NULL,
    created_by           BIGINT NOT NULL,
    updated_at           TIMESTAMP,
    updated_by           BIGINT,
    deleted_at           TIMESTAMP,
    deleted_by           BIGINT
);

CREATE TABLE "user" (
    id                   BIGINT PRIMARY KEY,

    first_name           VARCHAR(100) NOT NULL,
    last_name            VARCHAR(100) NOT NULL,
    email                VARCHAR(255) NOT NULL,
    phone                VARCHAR(25),

    primary_address_id   BIGINT NOT NULL,
    secondary_address_id BIGINT,

    created_at           TIMESTAMP NOT NULL,
    created_by           BIGINT NOT NULL,
    updated_at           TIMESTAMP,
    updated_by           BIGINT,
    deleted_at           TIMESTAMP,
    deleted_by           BIGINT,

    -- Foreign keys
    CONSTRAINT fk_user_primary_address
        FOREIGN KEY (primary_address_id)
        REFERENCES "address"(id),

    CONSTRAINT fk_user_secondary_address
        FOREIGN KEY (secondary_address_id)
        REFERENCES "address"(id)
);

CREATE TABLE "admin" (
    id                   BIGINT PRIMARY KEY,

    user_id              BIGINT NOT NULL,

    created_at           TIMESTAMP NOT NULL,
    created_by           BIGINT NOT NULL,
    updated_at           TIMESTAMP,
    updated_by           BIGINT,
    deleted_at           TIMESTAMP,
    deleted_by           BIGINT,

    -- Foreign keys
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES "address"(id),
);
