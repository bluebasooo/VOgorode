--liquibase formatted sql

--changeset bluebasooo:4
call insert_user_type_id(1000, 1);
alter table users drop column if exists user_type;