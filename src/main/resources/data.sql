insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Иван', 'Иванов', 'Иванович');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Андрей', 'Смирнов', 'Семёнович');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Владимир', 'Попов', 'Михайлович');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Дмитрий', 'Кузнецов', 'Васильевич');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Алексей', 'Попов', 'Михайлович');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Фёдор', 'Никитин', 'Алексеевич');
insert into client (id, first_name, last_name, middle_name) values (client_sequence.nextval, 'Иван', 'Михайлов', 'Сергеевич');


insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '439.67', '67849264719387461738', 'ACTIVE', 7);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '0', '67845378900123530678', 'ACTIVE', 5);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '1000.00', '67845437819785462890', 'ACTIVE', 4);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '27789.45', '67847878000963517354', 'DELETED', 2);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '645.32', '11119384738204736748', 'ACTIVE', 7);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '11.00', '67840031546734115680', 'ACTIVE', 3);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '1000000.00', '0000111122223333', 'INVALID', 1);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '99.99', '89427647381937560909', 'ACTIVE', 2);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '500.01', '12345678901234567890', 'INVALID', 1);
insert into account (id, account_balance, account_number, account_status, client_id) values (account_sequence.nextval, '0.00', '92129032125675458943', 'ACTIVE', 6);


insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4264111111111111', 'INVALID', '6');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4012001298892101', 'ACTIVE', '1');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4012000000000000', 'BLOCKED', '2');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4264895478643237', 'ACTIVE', '10');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4012001967833331', 'BLOCKED', '10');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '5299905623451132', 'ACTIVE', '5');
insert into card (id, card_number, card_status, account_id) values (card_sequence.nextval, '4012789312637164', 'ACTIVE', '3');


insert into counterparty (id, counterparty_status, name, account_id) values (counterparty_sequence.nextval, 'NEW', 'Контрагент Номер 1', '1');
insert into counterparty (id, counterparty_status, name, account_id) values (counterparty_sequence.nextval, 'ACTIVE', 'Контрагент Номер 2', '4');
insert into counterparty (id, counterparty_status, name, account_id) values (counterparty_sequence.nextval, 'ACTIVE', 'Контрагент Номер 3', '2');
insert into counterparty (id, counterparty_status, name, account_id) values (counterparty_sequence.nextval, 'INACTIVE', 'Контрагент Номер 4', '6');