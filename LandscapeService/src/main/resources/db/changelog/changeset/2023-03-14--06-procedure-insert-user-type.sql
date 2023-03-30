--liquibase formatted sql

--changeset bluebasooo:3
alter table users add column users_type_id smallint;
create index on users(login,id) where users.users_type_id is null;

create or replace procedure insert_user_type_id(page integer, time_stop integer)
as '
begin
    raise notice ''% - Changes starts with portion: %'', now(), page;

    while (select count(*) from users where users_type_id is null) > 0
        loop

            with pagination as (
                select *
                from users
                where users_type_id is null
                order by login, id
                limit page
            )

            update users
            set users_type_id = current_page.type_id
            from (
                select pagination.id as id, pagination.login as login, users_type.id as type_id
                from pagination join users_type
                on pagination.user_type = users_type.type
            ) as current_page
            where
            (users.login,users.id) = (current_page.login, current_page.id) and
            users.users_type_id is null;

            raise notice ''% - Committed % rows. Migration paused for % sec'', now(), page, time_stop;
            perform pg_sleep(time_stop);
        end loop;
end;
' language plpgsql;

-- call insert_user_type_id(100, 2);
-- alter table users drop column if exists user_type;