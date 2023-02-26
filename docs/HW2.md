# Tasks

---

- [x] Добавлен endpoint **[/connections](../LandscapeService/src/main/java/ru/tinkoff/landscape/controller/LandscapeController.java)** возвращающий хост,текущее состояние, версию и кол-во сервисов Handyman и Rancher
- [x] Реализован gRPC client [StatusService](../LandscapeService/src/main/java/ru/tinkoff/landscape/service/StatusService.java) в LandscapeService
- [x] Реализованы gRPC server в [Handyman](../HandymanService/src/main/java/ru/tinkoff/handyman/server/StatusServiceImpl.java) и [Rancher](../RancherService/src/main/java/ru/tinkoff/rancher/server/StatusServiceImpl.java)
- [x] endpoint **[/readiness](../HandymanService/src/main/java/ru/tinkoff/handyman/service/SystemService.java)** теперь возвращает StatesOfConnectivity (аналогично для [Rancher](../RancherService/src/main/java/ru/tinkoff/rancher/service/SystemService.java))
- [x] Добавлены [тесты](../HandymanService/src/test/java/ru/tinkoff/handyman/server/StatusServiceImplTest.java) для публичных методов grpc Server (Handyman и Rancher)
- [x] Добавлена документация для публичных методов сервисов