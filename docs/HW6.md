# Tasks

---

- [x] В сервисе **LandscapeService** реализованы REST _[эндпоинты](../LandscapeService/src/main/java/ru/tinkoff/landscape/controller/UserController.java)_ для CRUD операций с пользователями
- [x] В сервисе **LandscapeService** добавлена возможность _[хранить](../LandscapeService/src/main/java/ru/tinkoff/landscape/model/Location.java)_ долготу и широту посредством добавления новой сущности - _Location_ и добавление для него CRUD _[эндпоинтов](../LandscapeService/src/main/java/ru/tinkoff/landscape/controller/LocationController.java)_
- [x] К сервисам **[HandymanService](../HandymanService/src/main/resources/application.yml)** и **[RancherService](../RancherService/src/main/resources/application.yml)** была подключена MongoDB и также создана папка для миграций в resourses
- [x] В серивсах **[HandymanService](../HandymanService/src/main/java/ru/tinkoff/handyman/controller/HandymanController.java)** и **[RancherService](../RancherService/src/main/java/ru/tinkoff/rancher/controller/RancherController.java)** были реализованы эндпоинты для работы с профилем работника и участка + они создают сначала записи в **LandscapeService**
- [x] [Профиль](../HandymanService/src/main/java/ru/tinkoff/handyman/model/Handyman.java) работника содержит перечень работ которые он выполняет а также широту и долготу
- [x] [Провиль](../RancherService/src/main/java/ru/tinkoff/rancher/model/Rancher.java) участка привязан к профилю пользователя(владельца учатска) посредстом поля _login_ а также имеет координаты, площадь и список работ
- [x] Сервисы **[RancherService](../RancherService/src/main/java/ru/tinkoff/rancher/service/RancherService.java)** и **[HandymanService](../HandymanService/src/main/java/ru/tinkoff/handyman/service/HandymanService.java)** возвращают данные обогощенные **LandscapeService**
- [x] Написана документация для публичных методов сервисов
- [x] Добавлены интеграционные тесты для **[HandymanService](../HandymanService/src/test/java/ru/tinkoff/handyman/CrudTest.java)**, **[LandscapeService](../LandscapeService/src/test/java/ru/tinkoff/landscape/UserCrudTest.java)** (там же и для CRUD для Location) и **[Rancher](../RancherService/src/test/java/ru/tinkoff/rancher/CrudTest.java)**
- [x] Хранимая [процедура](../LandscapeService/src/main/resources/db/changelog/changeset/2023-03-14--06-procedure-insert-user-type.sql) из прошлого дз вынесена в отдельеный файл миграции
- [x] Измененен Dockerfile во всех сервисах для облегчения образа посредством multi-stage building - [например](../LandscapeService/Dockerfile.to_release)
- [x] Созданы Environment переменные для безопасности и удобства во всех сервисах - [например](../LandscapeService/src/main/resources/application.yml)