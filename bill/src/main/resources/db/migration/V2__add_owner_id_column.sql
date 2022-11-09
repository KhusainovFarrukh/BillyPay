ALTER TABLE bill
    ADD owner_id BIGINT;

UPDATE bill
    SET owner_id = 1;

ALTER TABLE bill
    ALTER COLUMN owner_id SET NOT NULL;