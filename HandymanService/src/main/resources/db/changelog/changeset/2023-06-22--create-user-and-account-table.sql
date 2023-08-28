--liquibase formatted sql

--changeset bluebasooo:1
create table if not exists users (
    id bigint generated always as identity,
    name varchar(20) not null,
    surname varchar(20) not null,
    skills text[] not null,
    email varchar(42) not null,
    phone varchar(20),
    photo bytea not null,

    primary key (id)
);

create type pay_system_type as enum ('Visa', 'MasterCard', 'Mir', 'UnionPay');

create table if not exists account(
    id bigint generated always as identity,
    user_id bigint,
    card_numbers varchar(16),
    pay_system pay_system_type,

    primary key (id),
    foreign key (user_id) references users (id)
);