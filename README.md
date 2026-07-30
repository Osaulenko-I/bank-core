# bank-core

Бэкенд-проект банковской системы на Spring Boot: пользователи, счета, переводы/пополнения/снятия, история транзакций и админ-панель с аутентификацией

## Стек

- Java 21
- Spring Boot 3, Spring Security (JWT)
- Spring Data JPA + PostgreSQL
- Gradle (multi-module), Docker / Docker Compose
- Springdoc OpenAPI (Swagger UI)

## Архитектура

Проект разбит на модули по слоям:

- **dataAccess** — сущности (`Account`, `User`, `AccountTransaction`) и Spring Data репозитории
- **service** — доменная логика, DTO, мапперы, сервисы (`AccountService`, `ClientService`, `AdminService`)
- **presentation** — REST-контроллеры, конфигурация Spring Security, точка входа приложения

## Основные возможности

- Регистрация и аутентификация пользователей (`/api/auth`)
- Управление счетами: создание, пополнение, снятие, перевод между счетами, просмотр баланса и истории транзакций (`/api/accounts`)
- Профиль пользователя и работа со списком друзей (`/api/users`)
- Админ-панель: управление пользователями и счетами, фильтрация по параметрам (`/api/admin`)

## Запуск

1. Поднять базу данных:
   ```bash
   cd docker
   cp .env.example .env   # заполнить своими значениями
   docker compose up -d
   ```
2. Собрать и запустить приложение:
   ```bash
   ./gradlew :presentation:bootRun
   ```
3. Приложение поднимется на `http://localhost:8080`, Swagger UI — `http://localhost:8080/swagger-ui.html`.

## Конфигурация

Параметры подключения к БД задаются через `application.properties`
