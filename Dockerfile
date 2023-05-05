#образ взятый за основу
FROM openjdk:17
ARG jarFile=target/taskmanager-0.1.war
#Куда переместить варник внутри контейнера
WORKDIR /opt/app
#копируем наш джарник в новый внутри контейнера
COPY ${jarFile} taskmanager-0.1.war
#Открываем порт
EXPOSE 9090
#Команда для запуска
ENTRYPOINT ["java", "-jar", "taskmanager-0.1.war"]
