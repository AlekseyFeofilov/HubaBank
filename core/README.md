# Микросервис CoreService
Сервис для управления счетами и транзакциями.
Хранит всю информацию о денежном балансе счетов.

## Сценарии
Ссылка на [диаграммы последовательностей](../diagrams)

## Документация API
Ссылка на [документацию API](openapi.json)

## Локальный запуск
1. Запустить [compose.yaml](compose.yaml) файл:
```
$ cd core
$ docker compose up -d
```
2. Запустить класс [HubabankCoreApplication](src/main/java/ru/hubabank/core/HubabankCoreApplication.java)

## Запуск тестов
1. Выполнить команду:
```
$ ./gradlew test
```

## Генерация документации API
1. Выполнить команду:
```
$ ./gradlew generateOpenApiDocs
```
2. Если возникает ошибка, то выполнить следующую команду:
```
$ ./gradlew generateOpenApiDocs --project-cache-dir=../cache
```

## Сборка микросервиса
1. Выполнить команду:
```
$ ./gradlew bootJar
```