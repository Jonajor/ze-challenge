FROM adoptopenjdk/openjdk11:latest

MAINTAINER "Jonathan Augusto"

COPY target/desafioze-0.0.1-SNAPSHOT.jar desafioze.jar

ENTRYPOINT ["java","-jar","/desafioze.jar"]