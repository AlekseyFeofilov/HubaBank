# Huba Bank
![](https://gitlab.com/hits-tsu/huba/badges/master/pipeline.svg)
![](https://gitlab.com/hits-tsu/huba/-/badges/release.svg)

# Микросервисы
- **Сервис "Кабинет клиента"** _(C#. ASPNET)_
  - [Исходный код](BFF-client)
  - [Стенд Staging](http://194.147.90.192:9001/swagger/index.html)

- **Сервис "Кабинет сотрудника"** _(C#. ASPNET)_
  - [Исходный код](employee-gateway)
  - [Стенд Staging](http://194.147.90.192:9002/swagger/index.html)

- **Сервис "Пользователи"** _(Java. Spring)_
  - [Исходный код](java/users)
  - [Стенд Staging](http://194.147.90.192:9003/swagger-ui/index.html)

- **Сервис "Ядро"** _(Java. Spring)_
  - [Исходный код](core)
  - [Стенд Staging](http://194.147.90.192:9004/swagger-ui/index.html)

- **Сервис "Кредиты"** _(C#. ASPNET)_
  - [Исходный код](credit)
  - [Стенд Staging](http://194.147.90.192:9005) - _скоро..._

# Мобильные приложения а
- **Мобильный кабинет клиента** (Android)
  - Исходный код - _скоро..._

- **Мобильный кабинет сотрудника** (IOS)
  - [Исходный код](ios-employee)

# Диаграммы
- [Все диаграммы последовательностей](diagrams)
- [Диаграммы последовательностей для сценариев клиента](diagrams/client)
- [Диаграммы последовательностей для сценариев сотрудника](diagrams/employer)

# Инфраструктура
- [Конфигурация CI](.gitlab-ci.yml)
- [Шаблоны CI](.infra/gitlab-ci-templates)
- [Dockerfile'ы](.infra/dockerfiles)
- [Конфигурация развертывания на Staging стенде](.infra/deploy/staging)
