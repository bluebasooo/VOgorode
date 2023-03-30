--liquibase formatted sql

--changeset bluebasooo:1
create extension if not exists "uuid-ossp";

create table if not exists users (
    id uuid default uuid_generate_v4(),
    login varchar(42) unique,
    email varchar(42) unique,
    phone varchar(20) unique,
    user_type varchar(10) not null,
    creates timestamp not null,
    updates timestamp not null,

    primary key (id)
);