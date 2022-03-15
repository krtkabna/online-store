CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(30) NOT NULL,
    password VARCHAR(42) NOT NULL
);
