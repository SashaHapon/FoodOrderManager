INSERT IGNORE INTO meal (name, price, time) VALUES
('Spaghetti Bolognese', 12.99, 30),
('Chicken Caesar Salad', 9.99, 20),
('Grilled Salmon', 15.99, 25),
('Test Meal 1', 15.99, 30),
('Test Meal 2', 15.99, 30),
('Test Meal 3', 15.99, 30),
('Test Meal 4', 15.99, 30);

INSERT IGNORE INTO account (name, money, phone_number) VALUES
('Test Account 1', 100.01, '1234567890'),
('Test Account 2', 100.01, '1234567890'),
('Test Account 3', 100.01, '1234567890'),
('Test Account 4', 100.01, '1234567890'),
 ('Sasha', 342.0, '+4245253562352')
;
INSERT IGNORE INTO orders ( id, meals_id, account_id, order_sum, cooking_time_sum, meal_id)
VALUES
  (1, 1, 1, 50.99, 30, 'Test Meal 1');
INSERT IGNORE INTO orders_meals (order_id, meals_id) VALUES
(1, 1),
(1, 2),
(1, 3);
