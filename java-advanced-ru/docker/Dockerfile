# Указываем образ, от которого наследуемся
FROM eclipse-temurin:20-jdk

# Установить версию Gradle
ARG GRADLE_VERSION=8.2

# Обновить пакеты и установить необходимые инструменты
RUN apt-get update && apt-get install -yq make unzip

# Скачать и распаковать Gradle
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && rm gradle-${GRADLE_VERSION}-bin.zip

# Установить домашний каталог Gradle
ENV GRADLE_HOME=/opt/gradle

# Переместить распакованный Gradle в домашний каталог
RUN mv gradle-${GRADLE_VERSION} ${GRADLE_HOME}

# Добавить Gradle в PATH
ENV PATH=$PATH:$GRADLE_HOME/bin

# Копируем директорию из основной файловой системы внутрь образа
COPY . /app

# Устанавливаем рабочую директорию
WORKDIR /app

# Собрать приложение
RUN gradle build

# Сообщаем, что контейнер использует 8080 порт
EXPOSE 8080

# Определяет действие, которое будет выполнено при старте контейнера (docker run)
# Используется только в случае, если контейнер был запущен без указания команды
# [команда, аргумент]
CMD ["java", "-jar", "/app/build/libs/docker.jar"]

