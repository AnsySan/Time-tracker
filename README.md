# Тестовое задание Тайм-трекер

## Требования

1. Организация структуры данных в базе данных PostgreSQL (user, record, project и т.д.).
2. Реализация CRUD операций посредством Spring Boot.
3. Миграции структуры данных (Flyway или Liquibase).
4. Авторизация запросов посредством Spring Security и JWT.
5. Разделение на роли в Spring Security (User может трекать время, Admin добавлять user и проекты для трекинга и т.д.).
6. Сборка приложения с помощью maven.
7. Развёртывание api и базы данных в Docker с использованием Docker compose.
8. Комментирование кода.
9. Чистый код и использование SOLID принципов.

## Технологии

- Java 17.
- Maven.
- PostgreSQL.
- Spring-boot-starter-data-jpa
- Spring-boot-starter-jdbc
- Spring-boot-starter-security
- Spring-boot-starter-web
- Spring-boot-starter-validation
- Liquibase
- Lombok

## Инструкция для запуска приложения
1. Для того чтобы приложения хорошо запускалось нужно чтобы все технологии строго были похожи на те которые описаны выше.
2. Все настройки проекта лежат в application.yml.
3. Для работы с базой данной был подключён liquibase и написаны 1 скрипта  для создания таблиц.
4. Запустить TimeTrackerApplication.java
5. Проект готов к работе.
