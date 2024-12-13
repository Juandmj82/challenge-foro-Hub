ALTER TABLE usuarios
ADD COLUMN login VARCHAR(255) NOT NULL;

UPDATE usuarios
SET login = email;