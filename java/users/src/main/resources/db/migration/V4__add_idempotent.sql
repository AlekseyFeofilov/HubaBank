CREATE TABLE idempotent (request_id uuid, body varchar(5000), status int, primary key (request_id));
