# ============================
#      Etapa 1: Build
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar proyecto completo
COPY pom.xml .
COPY src ./src

# Compilar usando Maven normal
RUN mvn -DskipTests package


# ============================
#      Etapa 2: Runtime
# ============================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el .jar generado
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]