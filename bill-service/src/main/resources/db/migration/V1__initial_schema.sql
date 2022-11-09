CREATE SEQUENCE IF NOT EXISTS bill_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE bill
(
    id             BIGINT           NOT NULL,
    address        VARCHAR(255),
    account_number VARCHAR(255)     NOT NULL,
    type           VARCHAR(255)     NOT NULL,
    price          DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_bill PRIMARY KEY (id)
);

ALTER TABLE bill
    ADD CONSTRAINT uk_bill_account_number UNIQUE (account_number);