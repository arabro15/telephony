CREATE TABLE IF NOT EXISTS public.customer
(
    id            uuid PRIMARY KEY,
    name          text      NOT NULL,
    year_of_birth text      NOT NULL,
    first_phone   text      NOT NULL,
    second_phone  text      NOT NULL,
    created_at    timestamp NOT NULL
);

INSERT INTO customer (id, name, year_of_birth, first_phone, second_phone, created_at)
VALUES
    (gen_random_uuid(), 'John Doe', '1985', '87771234567', '87772345678', now()),
    (gen_random_uuid(), 'Jane Smith', '1990', '87773456789', '87774567890', now()),
    (gen_random_uuid(), 'Michael Johnson', '1982', '87775678901', '87776789012', now()),
    (gen_random_uuid(), 'Emily Williams', '1988', '87777890123', '87778901234', now()),
    (gen_random_uuid(), 'Daniel Brown', '1979', '87779012345', '87770123456', now()),
    (gen_random_uuid(), 'Sophia Davis', '1989', '87771234567', '87772345678', now()),
    (gen_random_uuid(), 'David Miller', '1980', '87773456789', '87774567890', now()),
    (gen_random_uuid(), 'Olivia Wilson', '1987', '87775678901', '87776789012', now()),
    (gen_random_uuid(), 'James Garcia', '1983', '87777890123', '87778901234', now()),
    (gen_random_uuid(), 'Ava Rodriguez', '1984', '87779012345', '87770123456', now());