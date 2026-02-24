# **README.md**

```markdown
# JavaBistrot

JavaBistrot è il sistema tematizzato sul ristorante Nebbia&Zafferano, un locale di medio alto livello situato nel cuore di Milano. Il sistema è pensato per supportare maître e camerieri con strumenti semplici da usare, strutturati per ridurre errori, migliorare la comunicazione interna e garantire un servizio coerente. Le funzioni principali permettono di organizzare lo staff di sala, gestire il menu del ristorante, migliorare i flussi operativi e gestire le prenotazioni dei clienti.

Il principale target del sistema è il personale del ristorante. Abbiamo incluso nella parte di front end un form destinato ai clienti del ristorante per poter dimostrare le funzionalità principali riservate allo staff.

---

## Tecnologie utilizzate

- Java 21
- Spring Boot 4
  - Spring Web
  - Spring Data JPA
  - Spring Security (con BCrypt)
- PostgreSQL (ambiente di produzione)
- Hibernate (ORM)
- Maven
- Docker (multi-stage build)
- Thymeleaf (frontend)

---

## Funzionalità principali

### Gestione utenti
- Login tramite Spring Security
- Ruoli: MAITRE, USER
- Password cifrate con BCrypt

### Gestione piatti
- Creazione, modifica ed eliminazione dei piatti
- Categorie: Antipasti, Primi, Secondi, Dolci
- Descrizioni e prezzi configurabili

### Gestione prenotazioni
- Inserimento prenotazioni clienti
- Numero ospiti, note, orario
- Visualizzazione e gestione tramite pannello amministrativo

---

## Struttura del database

Il database è gestito tramite Hibernate.

---

## Configurazione ambiente

### Variabili d'ambiente (Render)

```

---

## Docker

Il progetto utilizza un Dockerfile multi-stage ottimizzato per ridurre i tempi di build.

### Build dell'immagine

```bash
mvn clean package -DskipTests
docker build -t javabistrot .
```

### Avvio del container

```bash
docker run -p 8080:8080 javabistrot
```

---

## Deploy su Render

1. Collegare la repository GitHub a Render.
2. Creare un servizio PostgreSQL.
3. Creare un Web Service basato sul Dockerfile.
4. Impostare le variabili d'ambiente.
5. Render eseguirà automaticamente:
   - build Docker
   - avvio del container
---

## Esecuzione locale

### 1. Avviare PostgreSQL tramite Docker

```bash
docker run --name pg-local \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=javabistrot \
  -p 5432:5432 \
  -d postgres:16
```

### 2. Configurare `application.properties` locale

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/javabistrot
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 3. Avviare l'applicazione

```bash
mvn spring-boot:run
```

---

## Struttura del progetto

```
JavaBistrot/
 ├── src/
 │   ├── main/
 │   │   ├── java/... (controller, service, repository, entity)
 │   │   ├── resources/
 │   │   │   ├── templates/ (Thymeleaf)
 │   │   │   ├── static/ (CSS, JS)
 │   │   │   ├── application.properties.example
 │   │   │   └── data.sql
 ├── Dockerfile
 ├── pom.xml
 └── README.md
```

---

## Team

Membri:
  - Loriana
  - Ornella
  - Mauro
  - Gabriele
  - Venelin

Progetto sviluppato dal team di JavaBistrot come esercitazione di sviluppo web full-stack con Java.


---

## Licenza

Questo progetto è distribuito con licenza MIT.
```

---