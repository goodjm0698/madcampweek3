FROM openjdk:11-jdk
ARG JAR_FILE=build/libs
LABEL authors="suzynoh"

ENTRYPOINT ["top", "-b"]