# Тестовое задание на позицию Java Junior разработчка

## Задача
> Создать сервис, который обращается к сервису курсов валют, и отдает gif в ответ:
если курс по отношению к рублю за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
если ниже - отсюда https://giphy.com/search/broke
Ссылки
REST API курсов валют - https://docs.openexchangerates.org/
REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
Must Have
Сервис на Spring Boot 2 + Java / Kotlin
Запросы приходят на HTTP endpoint, туда передается код валюты
Для взаимодействия с внешними сервисами используется Feign
Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
Для сборки должен использоваться Gradle
Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
Nice to Have
Сборка и запуск Docker контейнера с этим сервисом

## Установка и запуск

Для установки и запуска программного решения, пожалуйста, воспользуйтесь одним из двух предложенных вариантов 
(**процедуры описаны ниже**):
1. Использование исполняемого jar-файла
2. Использование docker контейнера

## Запуск с использованием jar-файла

Для запуска приложения, пожалуйста, введите перечисленные ниже команды в командной строке вашей операционной системы 
(приведен пример для windows), либо используйте [git bash](https://git-scm.com/downloads).
1. Загрузите проект с помощью [git](https://git-scm.com/downloads):
```
cd PATH/TO/YOUR/DIRECTORY
git clone https://github.com/nikshcherbakov/alpha-bank-test-task.git
```
* _PATH/TO/YOUR/DIRECTORY_ - путь, в который будет производиться сохранение

2. Выполните сборку проекта с помощью [Gradle](https://gradle.org/install/):
```
cd ./alpha-bank-test-task
gradle build
```

3. Запустите исполняемый jar-файл:
```
java -jar ./build/libs/alpha-bank-test-task-0.0.1-SNAPSHOT.jar
```

## Запуск с использованием docker

Для запуска с использованием docker образа, пожалуйста, убедитесь, что на Вашем компьютере установлен Docker. 
Для этого, введите в командную строку следующую команду: `docker -v` и убедитесь, что версия отображается 
корректно. В случае, если у Вас нет docker-а, то Вам потребуется 
[установить его](https://www.docker.com/products/docker-desktop), либо воспользоваться методом запуска с помощью 
исполняемого jar-файла.

1. Загрузите проект с помощью [git](https://git-scm.com/downloads):
```
cd PATH/TO/YOUR/DIRECTORY
git clone https://github.com/nikshcherbakov/alpha-bank-test-task.git
```
* _PATH/TO/YOUR/DIRECTORY_ - путь, в который будет производиться сохранение

2. Выполните сборку проекта с помощью [Gradle](https://gradle.org/install/):
```
cd ./alpha-bank-test-task
gradle build
```

3. Создайте docker образ
```
docker build -f Dockerfile -t alpha-bank-test .
```

* Для проверки того, что образ был успешно создан, пожалуйста, воспользуйтесь командой `docker images` и убедитесь, 
что образ _alpha-bank-test_ был успешно создан.

4. Запустите созданный на предыдущем шаге образ docker
```
docker run -p 8085:8085 alpha-bank-test
```
* В случае успешного запуска контейнера в командной строке появится надпись _Spring_.

## Использование

Чтобы воспользоваться сервисом после успешного запуска, пожалуйста, откройте любой браузер и воспользуйтесь 
следующим url:
```
http://localhost:8085/{currencyCode}
```
* Вместо *currencyCode* нужно использовать код интересующей валюты (трехсимвольный, заглавными буквами).

## HTTP endpoints

В приложении реализован один основной GetMapping `GET /{currencyCode}`, на который посылается код валюты, 
по отношению к которой выполняется анализ курса рубля.
* В случае, если подается несуществующий код валюты (или используются не только заглавные символы), то сервис 
  выведет на экран соответствующее сообщение об ошибке.
* Если пользователь введет код рубля (RUB), то на экране появится сообщение об ошибке (смотрится курс рубля к рублю).
* Если один из сервисов (с курсом обмена валюты или гифками) становится недоступным, либо происходит потеря интернет 
  соединения, пользователь будет об этом оповещен соответствующим сообщением на экране.