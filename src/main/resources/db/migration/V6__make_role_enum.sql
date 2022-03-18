CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');
ALTER TABLE users
    DROP COLUMN admin,
    ADD COLUMN role user_role;