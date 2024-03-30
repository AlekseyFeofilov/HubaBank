alter table bill add column currency varchar(3) default 'RUB';

alter table bill alter column type set default 'USER';
update bill set type = 'USER' where type is null;