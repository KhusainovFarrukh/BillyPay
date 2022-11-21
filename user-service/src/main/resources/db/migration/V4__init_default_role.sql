TRUNCATE TABLE role CASCADE;
ALTER SEQUENCE role_id_seq RESTART WITH 1;

SELECT nextval('role_id_seq');

INSERT INTO role (id, title, is_default)
VALUES (currval('role_id_seq'), 'User', TRUE);

COMMIT;

ALTER TABLE app_user
    ADD role_id BIGINT;

UPDATE app_user
SET role_id = currval('role_id_seq');

ALTER TABLE app_user
    ALTER COLUMN role_id SET NOT NULL;

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE app_user
DROP
COLUMN role;

