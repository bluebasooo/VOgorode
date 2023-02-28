# Tasks

---

- [x] Для каждого сервиса Dockerfile:
  - *[Handyman](../HandymanService/Dockerfile)*
  - *[Landscape](../LandscapeService/Dockerfile)*
  - *[Rancher](../RancherService/Dockerfile)*
- [x] Созданы
  - Директория **/dev**
  - *docker-compose.yml*
  - [README.md](../dev/README.md)
- [x] Написана [инструкция](../dev/README.md) по запуску с картинками демонстрации работоспособности
- [x] Написан *[docker-compose.yml](../dev/docker-compose.yml)* со всеми *сервисами* + *PostgreSQL*, *Prometheus*, *Grafana*
- [x] Добавлена ссылка в корневой [README.me](../README.md) с инструкцией по запуску
- [x] Реализован endpoint **/force/malfunction** у *[Handyman](../HandymanService/src/main/java/ru/tinkoff/handyman/controller/HandymanController.java)* и *[Rancher](../RancherService/src/main/java/ru/tinkoff/rancher/controller/RancherController.java)*
- [x] Добавлена документация для новых публичных методов
- [x] Добавлены тесты на новый endpoint:
  - *[Handyman](../HandymanService/src/test/java/ru/tinkoff/handyman/controller/HandymanControllerTest.java)*
  - *[Rancher](../RancherService/src/test/java/ru/tinkoff/rancher/controller/RancherControllerTest.java)*