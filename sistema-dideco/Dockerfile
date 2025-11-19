# ============================
#      Etapa 1: Build
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar todo el proyecto
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY src ./src

# Dar permisos a mvnw (importante)
RUN chmod +x mvnw

# Compilar
RUN ./mvnw -DskipTests package


# ============================
#      Etapa 2: Runtime
# ============================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el jar compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]