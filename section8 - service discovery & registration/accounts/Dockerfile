FROM maven:3.9.9-eclipse-temurin-23-alpine AS build

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

#Start with a base image containing Java runtime
FROM openjdk:23-jdk-slim

#Information around who maintains the image
LABEL authors="jarvis.com"

COPY --from=build /target/accounts-0.0.1-SNAPSHOT.jar .

# execute the application
CMD ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]