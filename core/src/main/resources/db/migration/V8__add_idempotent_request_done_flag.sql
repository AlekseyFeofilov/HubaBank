alter table idempotent_request add column done bool default true;

insert into property (name, type, value)
values ('delay-simulation-enabled', 'BOOLEAN', 'false'),
       ('simulated-delay-time', 'INT', '3000');