FROM openjdk:17-jdk-slim-buster AS build
WORKDIR /opt/app
COPY target/uzcard-milliy-demo.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
