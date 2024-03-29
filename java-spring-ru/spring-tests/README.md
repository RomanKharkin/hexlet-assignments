# Spring Tests

В этом домашнем задании вам предстоит протестировать уже готовое приложение на Spring Boot. Как и в предыдущем задании, тесты будем писать интеграционные.

## Ссылки

* [Документация по тестированию приложения](https://spring.io/guides/gs/testing-web/)
* [Класс MockMvc – предназначен для тестирования контроллеров, позволяет тестировать контроллеры без запуска http-сервера](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
* [Класс MockHttpServletResponse – представляет ответ сервера на HTTP-запрос](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mock/web/MockHttpServletResponse.html)
* [Класс MockMvcRequestBuilders – содержит методы запроса](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/request/MockMvcRequestBuilders.html)
* [Подключение Database Rider](https://github.com/database-rider/database-rider#spring)

## src/test/resources/application.yml

Нам необходима отдельная база данных, которая будет использоваться для тестирования приложения. Настройка подключения к тестовой базе выполняется в файле *src/test/resources/application.yml*

## Задачи

* Изучите файл с настройками базы для тестирования

## src/test/resources/datasets/people.yml

За наполнение базы данных тестовыми данными (фикстурами) отвечает библиотека *Database Rider* (нужная зависимость уже подключена к проекту).

## Задачи

* Изучите содержимое файла people.yml. Запрос из этого файла выполнится при старте приложения в тестах и наполнит базу тестовыми данными. Используйте эти данные при тестировании. Можете дополнить этот файл добавив дополнительные записи в базу.

## src/test/java/exercise/AppTest.java

Часть тестов для приложения уже написаны. Проверяется работа корневой страницы приложения, создание новой сущности и вывод конкретной сущности. Изучите эти тесты и комментарии к коду, они помогут вам при написании собственных тестов.

## Задачи

* Добавьте для класса `AppTest` аннотацию `@AutoConfigureMockMvc`, чтобы можно было запускать тесты без поднятия сервера. Пример можно посмотреть в документации по [ссылке](https://spring.io/guides/gs/testing-web/).

* Протестируйте вывод всех сущностей. Проверьте, что GET-запрос на */people* вернет код ответа 200 и список сущностей

* Протестируйте, как происходит обновление данных персоны в базе. Проверьте, что PATCH-запрос на адрес */people/{id}* возвращает код ответа 200 и обновляет данные персоны с указанным id

* Протестируйте удаление сущности из базы. Проверьте, что DELETE-запрос на адрес */people/{id}* возвращает код ответа 200 и удаляет сущность с указанным id

## Подсказки

* Изучите блок импортов в файле *AppTest.java*, он подскажет, какие методы используются для выполнения PATCH и DELETE запросов
