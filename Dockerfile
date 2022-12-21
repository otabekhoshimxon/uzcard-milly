



FROM openjdk:17-jdk-slim-buster AS build
COPY . .
RUN mvn clean package -DskipTests
WORKDIR /opt/app
EXPOSE 8080
COPY target/uzcard-milliy-demo.jar.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]