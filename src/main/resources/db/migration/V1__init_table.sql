CREATE TABLE products
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(30)   NOT NULL,
    price         NUMERIC(9, 2) NOT NULL,
    creation_date DATE DEFAULT Now()
);

INSERT INTO products (name, price, creation_date)
VALUES ('Mr. Proper', '9.99', '2009-01-01');
INSERT INTO products (name, price, creation_date)
VALUES ('Bucket', '0.99', '2013-11-08');
INSERT INTO products (name, price, creation_date)
VALUES ('Laptop', '1000', '2015-08-11');