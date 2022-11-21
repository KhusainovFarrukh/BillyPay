CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE role
(
    id         BIGINT       NOT NULL,
    title      VARCHAR(255) NOT NULL,
    is_default BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permissions
(
    role_id     BIGINT NOT NULL,
    permissions VARCHAR(255)
);

ALTER TABLE role
    ADD CONSTRAINT uk_role_title UNIQUE (title);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_role_permissions_on_role FOREIGN KEY (role_id) REFERENCES role (id);