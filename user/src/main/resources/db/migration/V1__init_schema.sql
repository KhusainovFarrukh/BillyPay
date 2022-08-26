CREATE SEQUENCE IF NOT EXISTS app_user_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE app_user
(
    id           BIGINT       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    is_enabled   BOOLEAN      NOT NULL DEFAULT TRUE,
    is_locked    BOOLEAN      NOT NULL DEFAULT FALSE,
    role         VARCHAR(255),
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uk_app_user_phone_number UNIQUE (phone_number);