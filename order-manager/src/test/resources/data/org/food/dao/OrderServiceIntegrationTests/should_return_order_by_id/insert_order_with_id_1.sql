INSERT INTO meal (id, name, price, time) VALUES
(1, 'Spaghetti Bolognese', 12.99, 30);

INSERT INTO account (id, name, money, phone_number) VALUES
(1,'Test Account 1', 100.01, '1234567890');
INSERT INTO orders ( id, meals_id, account_id, order_sum, cooking_time_sum, meal_id)
VALUES
  (1, 1, 1, 50.99, 30, 'Spaghetti Bolognese');
INSERT INTO orders_meals (order_id, meals_id) VALUES
(1, 1);
