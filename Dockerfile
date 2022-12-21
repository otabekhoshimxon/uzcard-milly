FROM openjdk:17-jdk-slim-buster

WORKDIR /opt/app

COPY target/uzcard-project-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]