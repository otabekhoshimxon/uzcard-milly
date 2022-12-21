



FROM openjdk:17-jdk-slim-buster AS build

WORKDIR /opt/app
EXPOSE 8080
COPY target/uzcard-milliy-demo.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
