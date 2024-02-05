INSERT IGNORE INTO users (username, password, email, role) VALUES
('user', 'password', 'user@example.com', 'ROLE_USER'),
('user1', 'password', 'user@example.com', 'ROLE_USER'),
('admin_user', '$2a$10$0r/A51nPCO9RHTIUGF5xheWBY2ydo5R.x9zUQRbsbs0rILX4tIlYC', 'admin@example.com', 'ROLE_ADMIN');
