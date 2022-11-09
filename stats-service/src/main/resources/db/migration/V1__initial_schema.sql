CREATE TABLE stats
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    start_date  date                  NOT NULL,
    end_date    date                  NULL,
    amount      DOUBLE                NOT NULL,
    total_price DOUBLE                NOT NULL,
    CONSTRAINT pk_stats PRIMARY KEY (id)
);