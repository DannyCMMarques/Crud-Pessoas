# Etapa 1: Construção da Aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
COPY src/main/resources /app/resources/

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar .

COPY --from=build /app/resources /app/resources

ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${ENVIRONMENT} $(ls *.jar)"]