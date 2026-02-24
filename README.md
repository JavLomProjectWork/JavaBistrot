# **README.md**


# JavaBistrot

JavaBistrot è un sistema è pensato per supportare il personale di sala di un ristorante con strumenti semplici da usare, strutturati migliorare la comunicazione con il cliente e garantire un migliore servizio di prenotazione e gestione del menù. Le funzioni principali permettono di organizzare lo staff di sala, gestire il menu del ristorante, le prenotazioni dei clienti e al contempo fornire delle pagine pubbliche che consentono al ristorante di presentarsi ai clienti e a questi di consultare il menù ed effettuare delle prenotazioni.
Come esempio, la parte pubblica del sito è stata tematizzata per un ipotetico ristorante milanese dal nome "Nebbia&Zafferano".

---

## Tecnologie utilizzate

- Java 21
- Spring Boot 4
  - Spring Web
  - Spring Data JPA
  - Spring Security (con BCrypt)
- MySql/MariaDB
- Hibernate (ORM)
- Maven
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

## Guida all'installazione

### 1. Clonare la repository

```bash
git clone https://github.com/JavLomProjectWork/JavaBistrot.git
```

### 2. Generare il DataBase

- Individuare un server che esegua MySql/MariaDB
- Eseguire DDL.sql per creare e strutturare il DB
- Eseguire DML.sql per popolare il DB

### 3. Configurare `application.properties` locale

Copiare il file src/main/resources/application.properties.example in src/main/resources/application.properties inserendo le coordinate del db e le impostazioni preferite.

### 4. Compilare l'applicazione

```bash
cd javabistrot
./mvnw clean package
```

### 5. Avviare l'applicazione

```bash
java -jar target/javabistrot-1.0.jar
```
L'applicazione sarà ora raggiungibile all'indirizzo configurato (di default http://localhost:8080).

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
 ├── pom.xml
 └── README.md
```

---

## Team

Membri:
  - [Loriana](https://github.com/lorianalodeserto)
  - [Ornella](https://github.com/ornella098)
  - [Mauro](https://github.com/Mauro7b5)
  - [Gabriele](https://github.com/gabrielegaro06)
  - [Venelin](https://github.com/vendevcode)

Progetto sviluppato dal team di JavaBistrot come esercitazione di sviluppo web full-stack con Java.


---
