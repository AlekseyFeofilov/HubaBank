alter table bill alter column user_id drop not null;
alter table bill add column type varchar;

alter table bill add constraint user_type_check
    check (user_id is null and type <> 'USER' or user_id is not null and type = 'USER');
alter table bill add constraint balance_check
    check (balance >= 0 );

insert into bill (user_id, type)
values (null, 'TERMINAL'),
       (null, 'LOAN');