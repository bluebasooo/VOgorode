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

