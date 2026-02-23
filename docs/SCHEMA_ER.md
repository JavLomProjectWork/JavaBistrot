# Schema ER - JavaBistrot Database

## Diagramma Entità-Relazione

```mermaid
erDiagram
    USER ||--o{ BOOKING : "assigns"
    
    
    USER {
        long id PK
        string username UK "UNIQUE"
        string password
        string role "ENUM: WAITER, MAITRE"
        boolean active
    }
    
    BOOKING_WAITERS {
        booking_id FK
        user_id FK
    }

    BOOKING {
        long id PK
        string customerName
        string email
        string phoneNumber
        int numberOfGuests "1-20"
        datetime bookingDateTime
        string notes "max 500 chars"
        boolean active
        long[] assignedWaiters FK "References User(id)"
    }
    
    DISH {
        long id PK
        string name
        string description "max 500 chars"
        decimal price "precision: 10, scale: 2"
        string type "ENUM: ANTIPASTO, PRIMO, SECONDO, DOLCE"
        boolean active
    }
```

## Descrizione delle Entità

### USER (Utenti)
Tabella che contiene gli utenti del sistema (camerieri e maître).

| Campo | Tipo | Vincoli | Descrizione |
|-------|------|---------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Identificativo univoco |
| username | VARCHAR | NOT NULL, UNIQUE | Nome utente |
| password | VARCHAR | NOT NULL | Password criptata |
| role | ENUM | NOT NULL | Ruolo: WAITER o MAITRE |
| active | BOOLEAN | | Indica se l'utente è attivo |

### BOOKING (Prenotazioni)
Tabella che contiene le prenotazioni dei clienti.

| Campo | Tipo | Vincoli | Descrizione |
|-------|------|---------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Identificativo univoco |
| customerName | VARCHAR | NOT NULL | Nome del cliente |
| email | VARCHAR | NOT NULL | Email del cliente |
| phoneNumber | VARCHAR(20) | NOT NULL | Numero di telefono |
| numberOfGuests | INT | NOT NULL | Numero di ospiti (1-20) |
| bookingDateTime | DATETIME | NOT NULL, FUTURE | Data e ora della prenotazione |
| notes | VARCHAR(500) | | Note sulla prenotazione |
| active | BOOLEAN | NOT NULL | Indica se la prenotazione è attiva |

### DISH (Piatti)
Tabella che contiene i piatti del menu.

| Campo | Tipo | Vincoli | Descrizione |
|-------|------|---------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Identificativo univoco |
| name | VARCHAR | NOT NULL | Nome del piatto |
| description | VARCHAR(500) | | Descrizione del piatto |
| price | DECIMAL(10,2) | NOT NULL, POSITIVE | Prezzo del piatto |
| type | ENUM | NOT NULL | Tipo: ANTIPASTO, PRIMO, SECONDO, DOLCE |
| active | BOOLEAN | NOT NULL | Indica se il piatto è disponibile |

### BOOKING_WAITERS (Tabella di giunzione)
Tabella che rappresenta la relazione molti-a-molti tra camerieri e prenotazioni.

| Campo | Tipo | Vincoli | Descrizione |
|-------|------|---------|-------------|
| booking_id | BIGINT | FK, PK | Riferimento a Booking(id) |
| waiter_id | BIGINT | FK, PK | Riferimento a User(id) |

## Relazioni

### USER - BOOKING (Many-To-Many)
- Un utente (cameriere) può essere assegnato a più prenotazioni
- Una prenotazione può avere più camerieri assegnati
- Gestita attraverso la tabella `booking_waiters`
- Con vincolo di integrità referenziale disabilitato (ConstraintMode.NO_CONSTRAINT)

## Enumerazioni

### Role (Ruoli utente)
- **WAITER**: Cameriere
- **MAITRE**: Maître (capo sala)

### DishType (Tipi di piatto)
- **ANTIPASTO**: Antipasti
- **PRIMO**: Piatti di pasta/riso
- **SECONDO**: Secondi piatti
- **DOLCE**: Dolci

---

