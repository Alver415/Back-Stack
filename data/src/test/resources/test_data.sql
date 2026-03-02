INSERT INTO addresses (id, street, city, state, zip_code, is_apartment) VALUES
(1, '123 Main St',        'Springfield', 'MA', '62701', FALSE),
(2, '456 Oak Ave',        'Springfield', 'MA', '62702', TRUE),
(3, '789 Pine Rd',        'Madison',     'CT', '53703', FALSE),
(4, '101 Maple Dr',       'Madison',     'CT', '53704', TRUE),
(5, '202 Cedar Blvd',     'Austin',      'RI', '73301', FALSE),
(6, '303 Birch Ln',       'Austin',      'RI', '73344', TRUE);

INSERT INTO users (
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
(1, 'Admin', 'User',    'admin@example.com',        '800-000-0001', 1, NULL, CURRENT_TIMESTAMP, NULL),
(2, 'John',  'Doe',     'john.doe@example.com',     '123-456-1001', 2, NULL, CURRENT_TIMESTAMP, 1),
(3, 'Jane',  'Smith',   'jane.smith@example.com',   '123-456-1002', 3, 4,    CURRENT_TIMESTAMP, 1),
(4, 'Alice', 'Johnson', 'alice.j@example.com',      '123-456-1003', 4, NULL, CURRENT_TIMESTAMP, 1),
(5, 'Bob',   'Brown',   'bob.brown@example.com',    '123-456-1004', 5, 6,    CURRENT_TIMESTAMP, 2),
(6, 'Carol', 'Davis',  'carol.d@example.com',       '123-456-1005', 6, NULL, CURRENT_TIMESTAMP, 2);
