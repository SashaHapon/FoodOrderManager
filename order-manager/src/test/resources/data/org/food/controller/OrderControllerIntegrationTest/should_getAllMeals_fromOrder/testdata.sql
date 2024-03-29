INSERT INTO meal (id, name, price, time) VALUES
(1, 'Spaghetti Bolognese', 12.99, 30),
(2, 'Chicken Caesar Salad', 9.99, 20),
(3, 'Grilled Salmon', 15.99, 25),
(4, 'Test Meal 1', 15.99, 30),
(5, 'Test Meal 2', 15.99, 30),
(6, 'Test Meal 3', 15.99, 30),
(7, 'Test Meal 4', 15.99, 30);

INSERT INTO account (id, name, money, phone_number) VALUES
(1,'Test Account 1', 100.01, '1234567890'),
(2,'Test Account 2', 100.01, '1234567890'),
(3,'Test Account 3', 100.01, '1234567890'),
(4,'Test Account 4', 100.01, '1234567890'),
 (5,'Sasha', 342.0, '+4245253562352')
;
INSERT INTO orders ( id, meals_id, account_id, order_sum, cooking_time_sum, meal_id)
VALUES
  (1, 1, 1, 50.99, 30, 'Test Meal 1'),
  (2, 1, 2, 50.99, 30, 'Test Meal 2'),
  (3, 1, 3, 50.99, 30, 'Test Meal 3'),
  (4, 1, 4, 50.99, 30, 'Test Meal 4');
INSERT INTO orders_meals (order_id, meals_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6);
