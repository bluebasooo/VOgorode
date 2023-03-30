# Tasks

---

- [x] Добавить сохранение состояние БД - был создан **[volume](../dev/docker-compose.yml)** еще в дз 3
- [x] Установить _minikube_
- [x] Раздеплено БД с персистентном хранилищем - добавлен _[PostgreSQL](../dev/k8s/postgres.yaml)_ и PV и PVC + добавлен _[config](../dev/k8s/postgres-config.yaml)_ для Postgres
- [x] Разденплоены сервисы с возможностью (например для _[Handyman](../HandymanService/src/main/resources/application.yml)_) подключения к БД:
  - _[Handyman](../dev/k8s/handyman.yaml)_
  - _[Landscape](../dev/k8s/landscape.yaml)_
  - _[Rancher](../dev/k8s/rancher.yaml)_