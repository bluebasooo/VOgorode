# Tasks

---

- [x] Репозиторий заведен + три отдельные сервиса
- [x] Каждый сервис работает на своем порту (80 Handyman, 8080 Landscape, 8000 Rancher) - endpoint реализован при помощи Spring Actuator (в [application.yml](../HandymanService/src/main/resources/application.yml))
- [x] Разные порты приложений (предыдущий пункт)
- [x] Есть endpoint **/metrics** у каждого сервиса (проипасан в [application.yml](../HandymanService/src/main/resources/application.yml))
- [x] У каждого приложения есть endpoint **/system/liveness** [пример](../HandymanService/src/main/java/ru/tinkoff/HandymanService/controller/HandymanController.java)
- [x] У каждого приложения есть endpoint **/system/readiness** [пример](../HandymanService/src/main/java/ru/tinkoff/HandymanService/controller/HandymanController.java)
- [x] Есть тесты на endpoint'ы [пример](../HandymanService/src/test/java/ru/tinkoff/HandymanService/HandymanServiceTests.java)