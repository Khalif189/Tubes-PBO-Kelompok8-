-- Import file ini di phpMyAdmin (tab Import / SQL)
-- Database: cleanhub

CREATE DATABASE IF NOT EXISTS cleanhub
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE cleanhub;

-- User (Customer / Staff / Admin)
CREATE TABLE users (
  id         VARCHAR(20)  NOT NULL PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  role       ENUM('Customer','Staff','Admin') NOT NULL,
  is_member  TINYINT(1)   NOT NULL DEFAULT 0
);

-- Katalog layanan
CREATE TABLE services (
  service_index INT          NOT NULL PRIMARY KEY,
  service_name  VARCHAR(100) NOT NULL,
  price         DECIMAL(12,2) NOT NULL
);

-- Pesanan
CREATE TABLE orders (
  order_id    VARCHAR(20)  NOT NULL PRIMARY KEY,
  customer_id VARCHAR(20)  NOT NULL,
  total_price DECIMAL(12,2) NOT NULL,
  status      VARCHAR(50)  NOT NULL DEFAULT 'Menunggu Diproses',
  complaint   TEXT         NOT NULL DEFAULT '-',
  created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (customer_id) REFERENCES users(id)
);

-- Detail layanan per pesanan (many-to-many)
CREATE TABLE order_services (
  order_id      VARCHAR(20) NOT NULL,
  service_index INT         NOT NULL,
  PRIMARY KEY (order_id, service_index),
  FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
  FOREIGN KEY (service_index) REFERENCES services(service_index)
);

-- Data awal (sama dengan seedData di Java)
INSERT INTO users (id, name, role, is_member) VALUES
  ('C-001', 'Budi',  'Customer', 1),
  ('C-002', 'Sari',  'Customer', 0),
  ('S-001', 'Andi',  'Staff',    0),
  ('A-001', 'Nadia', 'Admin',    0);

INSERT INTO services (service_index, service_name, price) VALUES
  (1, 'Cuci Kering',   30000),
  (2, 'Setrika',       20000),
  (3, 'Cuci Express',  45000);
