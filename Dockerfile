# ==========================================
# STAGE 1: Costruzione
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Esecuzione
# ==========================================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# 1. Copiamo tutti i .jar nella cartella corrente
COPY --from=build /app/target/*.jar ./

# 2. Eliminiamo il jar "plain" inutile e rinominiamo quello corretto (il fat jar) in app.jar
RUN rm -f *-plain.jar && mv *.jar app.jar

# 3. Avviamo l'app forzando Spring Boot a leggere la variabile $PORT fornita da Render
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]