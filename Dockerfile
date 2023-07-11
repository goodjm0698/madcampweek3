FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN apk update && apk add bash
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
# ENTRYPOINT ["java", "-jar", "/app.jar"]