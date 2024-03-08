insert into user_info(id, blocked_date, employee, first_name, second_name, third_name, password_hash, phone) VALUES
('d4ffa562-5c8d-4ef2-8ee4-000000000000', null, true, 'admin', 'admin', 'admin', 'admin', 'admin'),
('d4ffa562-5c8d-4ef2-8ee4-000000000001', null, true, 'employee', 'employee', 'employee', 'employee', 'employee'),
('d4ffa562-5c8d-4ef2-8ee4-000000000002', null, false, 'Павел', 'Павлов', 'Павлович', '1', '+79301111111'),
('d4ffa562-5c8d-4ef2-8ee4-000000000003', null, false, 'Антон', 'Антонов', 'Антонович', '1', '+79301111112'),
('d4ffa562-5c8d-4ef2-8ee4-000000000004', null, false, 'Эрен', 'Ягер', 'Михайлович', '1', '+79301111113'),
('d4ffa562-5c8d-4ef2-8ee4-000000000005', null, false, 'Итен', 'Михалков', 'Михайлович', '1', '+79301111114'),
('d4ffa562-5c8d-4ef2-8ee4-000000000006', '2024-03-07', false, 'Тарелка', 'ИзКоторой', 'ЯЕМ', '1', '+79301111115'),
('d4ffa562-5c8d-4ef2-8ee4-000000000007', null, false, 'Игрок', 'Любящий', 'Играть', '1', '+79301111116');

insert into role(description, name) VALUES
('client_role', 'CLIENT'),
('employers role', 'EMPLOYER');

insert into privilege(description, name) VALUES
('', 'BILL_READ'),
('', 'BILL_WRITE'),
('', 'TRANSACTION_READ'),
('', 'TRANSACTION_WRITE'),
('', 'BILL_READ_OTHERS'),
('', 'BILL_WRITE_OTHERS'),
('', 'TRANSACTION_READ_OTHERS'),
('', 'TRANSACTION_WRITE_OTHERS'),
('', 'ADMIN');

insert into role_privileges (privileges_name, role_name) VALUES
('BILL_READ', 'CLIENT'),
('BILL_WRITE', 'CLIENT'),
('TRANSACTION_READ', 'CLIENT'),
('TRANSACTION_WRITE', 'CLIENT'),
('BILL_READ_OTHERS', 'EMPLOYER'),
('BILL_WRITE_OTHERS', 'EMPLOYER'),
('TRANSACTION_READ_OTHERS', 'EMPLOYER'),
('TRANSACTION_WRITE_OTHERS', 'EMPLOYER');

insert into user_info_roles (user_id, roles_name) VALUES
('d4ffa562-5c8d-4ef2-8ee4-000000000000', 'EMPLOYER'),
('d4ffa562-5c8d-4ef2-8ee4-000000000001', 'EMPLOYER'),
('d4ffa562-5c8d-4ef2-8ee4-000000000002', 'CLIENT'),
('d4ffa562-5c8d-4ef2-8ee4-000000000003', 'CLIENT'),
('d4ffa562-5c8d-4ef2-8ee4-000000000004', 'CLIENT'),
('d4ffa562-5c8d-4ef2-8ee4-000000000005', 'CLIENT'),
('d4ffa562-5c8d-4ef2-8ee4-000000000006', 'CLIENT'),
('d4ffa562-5c8d-4ef2-8ee4-000000000007', 'CLIENT');

insert into user_info_addition_privileges(user_id, addition_privileges_name) VALUES
('d4ffa562-5c8d-4ef2-8ee4-000000000000', 'ADMIN');
