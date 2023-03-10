# Запуск кластера с сервисами и PostgeSQL

1. _Запускаем кластер:_
```bash
    minikube start --driver docker
```

2. _Деплоим приложения:_
```bash
    kubectl apply -f handyman.yaml
```
```bash
    kubectl apply -f landscape.yaml
```
```bash
    kubectl apply -f rancher.yaml
```

3. _Деплоим БД_
```bash
    kubectl apply -f postgres-config.yaml
```
```bash
    kubectl apply -f postgres.yaml
```

3. _Проверим что поды и сервисы активны:_
```bash
    kubectl get all
```

4. _Откроем туннели:_
```bash
    minikube service handyman-out --url
```
```bash
    minikube service landscape-out --url
```
```bash
    minikube service rancher-out --url
```

5. _Очищаем за собой_

Сервисы:
```bash
    kubectl delete svc --all 
```

Деплой:
```bash
    kubectl delete deployment --all 
```

