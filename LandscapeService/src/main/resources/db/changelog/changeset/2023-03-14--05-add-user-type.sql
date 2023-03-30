--liquibase formatted sql

--changeset bluebasooo:2
create table if not exists users_type (
    id smallint primary key,
    type varchar(10) not null
);

insert into users_type(id, type) values
(1, 'handyman'),
(2, 'rancher');

