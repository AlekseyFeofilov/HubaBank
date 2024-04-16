create table if not exists property (
    name varchar,
    type varchar not null,
    value varchar not null,
    primary key (name)
);

insert into property (name, type, value)
values ('error-simulation-enabled', 'BOOLEAN', 'true'),
       ('error-simulation-default-change', 'DOUBLE', '0.5'),
       ('error-simulation-increased-change', 'DOUBLE', '0.9');