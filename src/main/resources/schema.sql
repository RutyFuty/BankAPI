create sequence account_sequence
    start with 1
    increment by 1;

create sequence card_sequence
    start with 1
    increment by 1;

create sequence client_sequence
    start with 1
    increment by 1;

create sequence counterparty_sequence
    start with 1
    increment by 1;

create sequence transaction_sequence
    start with 1
    increment by 1;


create table account (
    id bigint not null,
     account_balance decimal(19,2),
      account_number varchar(255),
       account_status varchar(255),
        client_id bigint not null,
         primary key (id));

create table card (
    id bigint not null,
     card_number varchar(255),
      card_status varchar(255),
       account_id bigint not null,
        primary key (id));

create table client (
    id bigint not null,
     first_name varchar(255),
      last_name varchar(255),
       middle_name varchar(255),
        primary key (id));

create table counterparty (
    id bigint not null,
     counterparty_status varchar(255),
      name varchar(255),
       account_id bigint not null,
        primary key (id));

create table transaction (
    id bigint not null,
     tx_amount decimal(19,2),
      tx_date_time time,
       tx_status varchar(255),
        dst_account_id bigint,
         src_account_id bigint,
          primary key (id));


alter table account
    add constraint UNIQUEaccNUMBER
        unique (account_number);

alter table card
    add constraint UNIQUEcardNUMBER
        unique (card_number);

alter table account
    add constraint FKclientID
        foreign key (client_id) references client;

alter table card
    add constraint FKcardACCID
        foreign key (account_id) references account;

alter table counterparty
    add constraint FKcntpartACCID
        foreign key (account_id) references account;

alter table transaction
    add constraint FKtransDSTACCID
        foreign key (dst_account_id) references account
            on delete cascade;

alter table transaction
    add constraint FKtransSRCACCID
        foreign key (src_account_id) references account
            on delete cascade;
