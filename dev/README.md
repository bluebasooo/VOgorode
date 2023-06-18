# Сборка проекта

---

### 1. Сборка сервисов

Сходим во все проектные директории проекта и соберем их при помощи **gradle** - для этого воспользуемся командами `cd` и `gradlew bootJar` :
 - *HandymanService*
```bash
    cd HandymanService && gradlew bootJar && cd ..
```
 - *LandscapeService*
```bash
    cd LandscapeService && gradlew bootJar && cd ..
```
 - *RancherService*
```bash
    cd RancherService && gradlew bootJar && cd ..
```

![building_project](pictures/build_projects.png)

---

### 2. Сборка контейнеров

Здесь нам понадобится **docker**.

Чтобы собрать контейнеры выполним команду `docker-compose build` в **/dev** директории:
```bash
    cd dev && docker-compose build && cd ..
```

![docker-compose build](pictures/docker-compose_build.png)

Далее запустим контейнеры при помощи команды `docker-compose up` 
```bash
    cd dev && docker-compose up && cd ..
```

![docker-compose up](pictures/docker-compose_up.png)
Ура! - приложение запущено.

### 3. Потыкаем приложение

Зайдем в наш любимый браузер и убедимся что наши сервисы рабоают:
- *LandscapeService* - сходим по адресу *<localhost:8080/system/readiness>*

![Landscape state](pictures/LS_test.png)
Ура! LandscapeSevice на лету

- *HandymanService* и *RancherService* - сходим по адресу *<localhost:8080/system/conections>*

![Handyman and Rancher states](pictures/HS_RS_test.png)
Победа! Наши сервисы рабоют

Теперь посмотрим на метрики - grafana и prometheus:
- *grafana* - перейдем по *<localhost:3000>*

![grafana](pictures/grafana_test.png)

- *prometheus* - *<localhost:9090>*

![prometheus](pictures/prometheus_test.png)

---

# Инструкция по поднятию Prometheus + Grafana

### 0. Используем прыдущую инструкцию для сборки проекта

### 1. Запускаем контейнер
Для начала надо попасть в коренную папку проекта.

Далее запустить контейнеры при помощи команды `docker-compose up`
```bash
    cd dev && docker-compose up && cd ..
```

### 2. Пользуемся

2.0 Посмотрим на метрики - grafana и prometheus:
- *grafana* - перейдем по *<localhost:3000>*

2.1 Авторизуемся - логин и пароль в файле _docker-compose.yml_ (по умолчанию login: **admin**, password: **admin**)

![grafana](pictures/grafana_login.png)

2.2 После входа идем в Dashboards

![grafana](pictures/grafana_test.png)

2.3 Выбираем понравившийся dashboard

![grafana](pictures/grafana_dashboards.png)

2.4 Наши метрики

![grafana](pictures/grafana_example_dashboard.png)
