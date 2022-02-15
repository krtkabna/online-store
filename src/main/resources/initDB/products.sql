DROP TABLE IF EXISTS products;

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(30) NOT NULL,
                          price MONEY NOT NULL,
                          creation_date DATE NOT NULL
);

INSERT INTO products (name, price, creation_date) VALUES ( 'Mr. Proper', '9.99', '2009-01-01');
INSERT INTO products (name, price, creation_date) VALUES ( 'Bucket', '0.99', '2013-11-08');
INSERT INTO products (name, price, creation_date) VALUES ( 'Laptop', '1000', '2015-08-11');
INSERT INTO products (name, price, creation_date) VALUES ( 'Chair', '30', '2019-01-01');
INSERT INTO products (name, price, creation_date) VALUES ( 'Table', '100', '2007-09-11');
INSERT INTO products (name, price, creation_date) VALUES ( 'Couch', '200', '2009-01-01');
INSERT INTO products (name, price, creation_date) VALUES ( 'Bourbon', '20', '2018-11-01');
INSERT INTO products (name, price, creation_date) VALUES ( 'Plastic bag', '0.01', '2022-02-14');