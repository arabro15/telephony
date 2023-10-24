# telephony - Тестовое задание greetgo!
 
**Используемые технологий:** Java 17, Gradle 8.3, Clean architecture, Spring (Boot 3.1.5, Web, Data MongoDB) , MyBatis, 
Lombok, Migration(Flyway, Mongock), PosgreSQL, MongoDB , REST API, JUnit и Mocikto.

# Начало работы:
---
Необходимо версия Java - 17 
1. Клонируйте или скачайте проект 

  - Для клонирование введите команду
    ```
    git clone https://github.com/arabro15/telephony
    ```
  - Либо скачайте его .zip файлом и разархивируйте. 
  
2. Откройте проект и запустите Docker
3. Откройте терминал в папке проекта

  - Введите команду
    ```docker
    docker-compose up -d --build
    ```
    
4. Запустите приложение
5. Сделайте импорт файл `telephony.postman_colletion.json` в Postman
6. Приступать к тестированию через postman

---
# REST API  
### [Просмотреть REST API](RESTAPI.md)
---
# Настройки Docker
### [Посмотреть `docker-compose.yml`](docker-compose.yml)
#### Настройки БД PostgreSQL
`version:` postgres:15.3-alpine <br />
`user:` local_pg_user <br />
`password:` y0ONKIOdCaj2n23 <br />
`database:` local_telephony_posgresql <br />
`host:` localhost <br />
`port:` 5438 <br />
#### Настройки БД MongoDB
`version:` mongo:latest (7.0.2) <br />
`user:` local_mongo_user <br />
`password:` 106fe2e9be0e4016a60f68ad40c9451a <br />
`host:` localhost <br />
`port:` 27039 <br />
#### Env файлы находятся в папке [`docker/env`](docker/env)
---
# Файл с коллекциями для Postman
### [Посмотреть `telephony.postman_collection.json`](telephony.postman_collection.json)
---
