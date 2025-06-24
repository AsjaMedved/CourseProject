# AQA Shop - Автоматизация тестирования
Автоматизированное тестирование веб-приложения для покупки туров.
Проверяются UI, API и взаимодействие с базами данных (PostgreSQL и MySQL).
Реализованы тесты на успешную и неуспешную оплату,

## Предустановка
Перед запуском автотестов необходимо установить:
- Docker
- Java 11+
- IntelliJ IDEA
- Allure

## Настройка зависимостей 
В корне проекта в файле build.gradle указаны зависимости для:
- JUnit 5
- Selenide
- REST Assured
- Драйверы баз данных (PostgreSQL и MySQL)
- Плагина Allure
- Lombok

## Запуск БД в Docker
В корне проекта находится файл docker-compose.yml. Для запуска двух баз данных (PostgreSQL и MySQL) выполните команду:
- docker-compose up -d

## Запуск приложения
* В папке artifacts находится JAR-файл: aqa-shop.jar
* Для каждого типа базы данных используются отдельные конфигурационные файлы (в корне проекта):
  * application-postgres.properties — для PostgreSQL
  * application-mysql.properties — для MySQL
* Команды для запуска:
- для с Postgres: java -jar artifacts/aqa-shop.jar --spring.profiles.active=postgres
- для MySQL: java jar artifacts/aqa-shop.jar --spring.profiles.active=mysql

## Запуск автотестов
Тесты написаны с использованием JUnit 5 + Selenide + REST Assured.
Команды для запуска:
- Для PostgreSQL: ./gradlew clean test "-Dspring.profiles.active=postgres"
- Для MySQL: ./gradlew clean test "-Dspring.profiles.active=mysql"

## Генерация отчета Allure
Для запуска и просмотра отчёта:
- ./gradlew allureServe
