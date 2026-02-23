# ==========================================
# STAGE 1: Costruzione (Compila il codice)
# ==========================================
# Usiamo un'immagine Maven compatibile con Java 21 per compilare
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamo prima il pom.xml e poi i sorgenti
COPY pom.xml .
COPY src ./src

# Facciamo creare a Maven il file .jar (questa istruzione crea la famosa cartella target)
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Esecuzione (Avvia l'app)
# ==========================================
# Usiamo l'immagine ufficiale OpenJDK 21 che avevi scelto tu
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Magia: copiamo il .jar generato nel primo stage (AS build), non da GitHub!
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on (default Spring Boot is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]FROM eclipse-temurin:21-jdk