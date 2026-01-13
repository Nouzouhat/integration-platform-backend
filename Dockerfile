# Étape 1 : Build de l'application avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY src ./src

# Builder l'application (skip tests pour accélérer)
RUN mvn clean package -DskipTests

# Étape 2 : Image finale avec Java
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Variable d'environnement pour Spring Boot
ENV SPRING_PROFILES_ACTIVE=docker

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]