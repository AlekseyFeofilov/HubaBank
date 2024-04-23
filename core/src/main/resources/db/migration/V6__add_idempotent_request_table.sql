create table if not exists idempotent_request (
    method varchar not null,
    request varchar not null,
    idempotent_key varchar not null,
    primary key (method, request, idempotent_key)
);