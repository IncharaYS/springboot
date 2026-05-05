INSERT INTO users
(customer_id, name, email, phone, address, password, role, active, created_at, updated_at, deleted)
VALUES
(1000, 'System Admin', 'admin@gmail.com', '9876543210',
 'Head Office',
 '$2a$10$BUTWTCZvK3xpE1JNpI66TO2hYYv7KX6a4xsAYxHZWWVq5tNxXqH5e',
 'ADMIN',
 true,
 NOW(),
 NOW(),
 false);


INSERT INTO cards
(card_type, card_number, card_holder_name, expiry_month, expiry_year, cvv, active, created_at, updated_at, deleted)
VALUES
('CREDIT_CARD', '1111222233334444', 'John Doe', 12, 2030, '123', true, NOW(), NOW(), false),
('DEBIT_CARD',  '5555666677778888', 'Jane Smith', 11, 2029, '456', true, NOW(), NOW(), false),
('CREDIT_CARD', '9999000011112222', 'Michael Brown', 10, 2031, '789', true, NOW(), NOW(), false),
('DEBIT_CARD',  '3333444455556666', 'Emily Davis', 9, 2028, '321', true, NOW(), NOW(), false),
('CREDIT_CARD', '7777888899990000', 'David Wilson', 8, 2032, '654', true, NOW(), NOW(), false),
('DEBIT_CARD',  '1212121212121212', 'Sophia Taylor', 7, 2027, '987', true, NOW(), NOW(), false),
('CREDIT_CARD', '3434343434343434', 'James Anderson', 6, 2033, '147', true, NOW(), NOW(), false),
('DEBIT_CARD',  '5656565656565656', 'Olivia Thomas', 5, 2026, '258', true, NOW(), NOW(), false),
('CREDIT_CARD', '7878787878787878', 'William Jackson', 4, 2034, '369', true, NOW(), NOW(), false),
('DEBIT_CARD',  '9090909090909090', 'Ava White', 3, 2035, '159', true, NOW(), NOW(), false);