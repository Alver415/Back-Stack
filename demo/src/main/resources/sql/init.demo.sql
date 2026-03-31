
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "address";
DROP TABLE IF EXISTS "place";

CREATE TABLE "place" (
    id                  BIGINT PRIMARY KEY,
    title               VARCHAR(64) NOT NULL,
    country             VARCHAR(64) NOT NULL,
    continent           VARCHAR(64) NOT NULL,

    created_at           TIMESTAMP NOT NULL,
    created_by           BIGINT NOT NULL,
    updated_at           TIMESTAMP,
    updated_by           BIGINT,
    deleted_at           TIMESTAMP,
    deleted_by           BIGINT
)

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

INSERT INTO "place" (
    id,
    title,
    country,
    continent,
    created_at,
    created_by
) VALUES
(1, 'New York',      'United States', 'North America', CURRENT_TIMESTAMP, 1),
(2, 'Paris',         'France',        'Europe',        CURRENT_TIMESTAMP, 1),
(3, 'Mount Everest', 'Nepal',         'Asia',          CURRENT_TIMESTAMP, 1),

INSERT INTO "address" (
    id,
    street,
    city,
    state,
    zip_code,
    is_apartment,
    created_at,
    created_by
) VALUES
(1, '123 Main St',        'Springfield', 'MA', '62701', FALSE, CURRENT_TIMESTAMP, 1),
(2, '456 Oak Ave',        'Springfield', 'MA', '62702', TRUE, CURRENT_TIMESTAMP, 1),
(3, '789 Pine Rd',        'Madison',     'CT', '53703', FALSE, CURRENT_TIMESTAMP, 1),
(4, '101 Maple Dr',       'Madison',     'CT', '53704', TRUE, CURRENT_TIMESTAMP, 1),
(5, '202 Cedar Blvd',     'Austin',      'RI', '73301', FALSE, CURRENT_TIMESTAMP, 2),
(6, '303 Birch Ln',       'Austin',      'RI', '73344', TRUE, CURRENT_TIMESTAMP, 2);

INSERT INTO "user" (
    id,
    first_name,
    last_name,
    email,
    phone,
    primary_address_id,
    secondary_address_id,
    created_at,
    created_by
) VALUES
(1, 'Admin', 'User',    'admin@example.com',        '800-000-0001', 1, NULL, CURRENT_TIMESTAMP, 1),
(2, 'John',  'Doe',     'john.doe@example.com',     '123-456-1001', 2, NULL, CURRENT_TIMESTAMP, 1),
(3, 'Jane',  'Smith',   'jane.smith@example.com',   '123-456-1002', 3, 4,    CURRENT_TIMESTAMP, 1),
(4, 'Alice', 'Johnson', 'alice.j@example.com',      '123-456-1003', 4, NULL, CURRENT_TIMESTAMP, 1),
(5, 'Bob',   'Brown',   'bob.brown@example.com',    '123-456-1004', 5, 6,    CURRENT_TIMESTAMP, 2),
(6, 'Carol', 'Davis',  'carol.d@example.com',       '123-456-1005', 6, NULL, CURRENT_TIMESTAMP, 2);
