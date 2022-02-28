DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL
);

INSERT INTO users (name, password)
VALUES ('admin', 'admin');
INSERT INTO users (name, password)
VALUES ('hello', 'world');