-- ========================================
-- HostelMart Database Setup Script
-- ========================================
-- Execute this SQL file to set up the database

-- Create Database
CREATE DATABASE IF NOT EXISTS hostelmart;
USE hostelmart;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Products Table
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Display tables to verify creation
SHOW TABLES;

-- Display table structures
DESCRIBE users;
DESCRIBE products;

-- Optional: Insert sample data (for testing)
-- Uncomment below to add sample data

-- INSERT INTO users (name, email, password) VALUES 
-- ('Raj Kumar', 'raj@email.com', 'password123'),
-- ('Priya Singh', 'priya@email.com', 'secure@123');

-- INSERT INTO products (name, price, quantity) VALUES 
-- ('Hostel Bed Sheet', 299.99, 50),
-- ('Pillow Case', 149.99, 100),
-- ('Mattress Protector', 399.99, 30),
-- ('Blanket', 249.99, 40),
-- ('Towel', 99.99, 150);
