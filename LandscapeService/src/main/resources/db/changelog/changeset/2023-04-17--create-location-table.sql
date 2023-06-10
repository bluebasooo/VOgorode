--liquibase formatted sql

--changeset bluebasooo:5
create table if not exists location(
    user_id uuid,
    longitude double precision ,
    latitude double precision ,

    primary key (user_id),
    foreign key (user_id) references users (id)
)