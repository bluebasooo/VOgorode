# Tasks

---

- [x] Во все сервисы были добавлены аспекты обеспечивающие сбор метрик по длительности работы endpoint-ов -> обернуты в аннотацию Timed - _[например](../HandymanService/src/main/java/ru/tinkoff/handyman/controller/HandymanController.java)_
- [x] Подняты сервисы Prometheus и Grafana, а также они имеют персистентное состояние - _[compose-файл](../dev/docker-compose.yml)_
- [x] Настроены сборы метрик и отображения на бордах графаны(сложены в папке _[monitoring](../dev/monitoring/grafana/dashboards)_) а также добавлены метрики на JVM, входящий траффик, процентное содержание ошибок и latency
- [ ] Борды на DB - нет
- [x] Видеоинструкция на падлете
- [x] Добавлена инструкция по поднятию - _[здесь](../dev/README.md)_