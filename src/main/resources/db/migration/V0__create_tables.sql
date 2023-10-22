CREATE TABLE IF NOT EXISTS public.customer
(
    id            uuid PRIMARY KEY,
    name          text      NOT NULL,
    year_of_birth text      NOT NULL,
    first_phone   text      NOT NULL,
    second_phone  text      NOT NULL,
    created_at    timestamp NOT NULL
);