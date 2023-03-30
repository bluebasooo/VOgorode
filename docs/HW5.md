# Tasks

---

- [x] Создана _[директория](../LandscapeService/src/main/resources/db)_ **_db_** в папке _/resources_
- [x] Директория содержит 3 файла
- [x] Создана миграция с DDL для таблицы пользователей - _[changeset - 1](../LandscapeService/src/main/resources/db/changelog/changeset/2023-03-14--01-create-user-table.sql)_
- [x] Нагенерирована _[стабина](https://disk.yandex.ru/d/mTdv_wa4oFmuEw)_
    - _stabina.sql_ - содержит в себе 3,14M записей
    - _stabina2.sql_ - содержит в себе 10_000 записей - была протестирована - успешно
    - сама по себе папка содержит генератор стабины - любой может воспользоваться ей и нагенерировать свою собственную стабину
- [x] Создана миграция с созданием таблицы типов пользователей, а также миграция заменяющаяя тип в таблице с пользователями - _[changeset-2](../LandscapeService/src/main/resources/db/changelog/changeset/2023-03-14--05-add-user-type.sql)_ и _[changeset-3](../LandscapeService/src/main/resources/db/changelog/changeset/2023-03-14--06-procedure-insert-user-type.sql)_
- [x] Создана хранимая процедура _[insert_user_type_id](../LandscapeService/src/main/resources/db/changelog/changeset/2023-03-14--06-procedure-insert-user-type.sql)_