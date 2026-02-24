CREATE DATABASE IF NOT EXISTS javabistrot;
USE javabistrot;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    active BOOLEAN
);

CREATE TABLE dishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    number_of_guests INT NOT NULL,
    booking_date_time DATETIME NOT NULL,
    notes VARCHAR(500),
    active BOOLEAN NOT NULL
);

CREATE TABLE booking_waiters (
    booking_id BIGINT NOT NULL,
    waiter_id BIGINT NOT NULL,
    PRIMARY KEY (booking_id, waiter_id)
);