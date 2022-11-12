CREATE TABLE notification
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    message VARCHAR(255)          NOT NULL,
    user_id BIGINT                NOT NULL,
    sent_at datetime              NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);