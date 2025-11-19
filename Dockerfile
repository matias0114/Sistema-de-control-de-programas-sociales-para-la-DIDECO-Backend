# Etapa 1: build
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY sistema-dideco/pom.xml .
COPY sistema-dideco/src ./src

RUN mvn -e -X -DskipTests package

# Etapa 2: runtime
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]