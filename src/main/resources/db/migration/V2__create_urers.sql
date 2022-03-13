DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL
);
