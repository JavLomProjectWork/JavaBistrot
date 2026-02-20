# Booking Creation Flow - Activity Diagram

```mermaid
graph TD
    Start([Utente compila form<br/>prenotazione]) --> Input["POST /private/bookings/add<br/>(BookingDTO)"]
    Input --> ReceiveCtrl["BookingManageController<br/>riceve richiesta"]
    ReceiveCtrl --> CallService["BookingService<br/>createBooking()"]
    CallService --> MapDTO["BookingMapper<br/>toEntity(DTO)"]
    MapDTO --> EntityCreated["Booking entity creato"]
    EntityCreated --> ValidateData{"Dati validi?"}
    
    ValidateData -->|No| ErrorValidate["Errore validazione"]
    ErrorValidate --> ShowError1["Messaggio errore"]
    ShowError1 --> End1([Torna al form])
    
    ValidateData -->|Sì| SaveDB["BookingRepo.save()<br/>INSERT INTO bookings"]
    SaveDB --> DBConfirm["Prenotazione salvata<br/>nel database"]
    DBConfirm --> CheckConflict{"Conflitti<br/>di orario?"}
    
    CheckConflict -->|Sì| ErrorConflict["Data/Ora occupata"]
    ErrorConflict --> ShowError2["Suggerisci orari liberi"]
    ShowError2 --> End2([Torna al form])
    
    CheckConflict -->|No| ReturnEntity["Ritorna Booking persistito"]
    ReturnEntity --> Redirect["Redirect a /private/bookings/manage"]
    Redirect --> Success["Prenotazione creata!"]
    Success --> End3(["Completato"])
    
    style Start stroke:#27AE60,stroke-width:2px
    style Input stroke:#9B59B6,stroke-width:2px
    style ReceiveCtrl stroke:#F39C12,stroke-width:2px
    style CallService stroke:#1ABC9C,stroke-width:2px
    style MapDTO stroke:#E67E22,stroke-width:2px
    style EntityCreated stroke:#4A90E2,stroke-width:2px
    style ValidateData stroke:#F39C12,stroke-width:2px
    style SaveDB stroke:#E74C3C,stroke-width:2px
    style DBConfirm stroke:#4A90E2,stroke-width:2px
    style CheckConflict stroke:#F39C12,stroke-width:2px
    style ErrorValidate stroke:#E74C3C,stroke-width:2px
    style ErrorConflict stroke:#E74C3C,stroke-width:2px
    style ShowError1 stroke:#E74C3C,stroke-width:2px
    style ShowError2 stroke:#E74C3C,stroke-width:2px
    style ReturnEntity stroke:#27AE60,stroke-width:2px
    style Redirect stroke:#1ABC9C,stroke-width:2px
    style Success stroke:#27AE60,stroke-width:2px
    style End1 stroke:#E74C3C,stroke-width:2px
    style End2 stroke:#E74C3C,stroke-width:2px
    style End3 stroke:#27AE60,stroke-width:2px
```

## Flusso di Creazione Prenotazione

| Fase | Componente | Azione |
|------|-----------|--------|
| Input | Form HTML | Utente compila form con dati prenotazione |
| Controller | BookingManageController | Riceve richiesta POST |
| Service | BookingService | Avvia logica di business |
| Mapping | BookingMapper | Converte DTO in entity |
| Validazione | Service | Verifica dati (email, telefono, ospiti, ecc) |
| Database | BookingRepo | Salva nel database |
| Controllo | Service | Verifica conflitti di orario |
| Output | Controller | Redirige a gestione prenotazioni |

## Percorsi di Successo / Errori

- **Validation Error** → Mostra errore validazione
- **Time Conflict** → Suggerisce orari liberi
- **Success** → Prenotazione creata e visibile in gestione

## Componenti Coinvolti

| Componente | Ruolo |
|-----------|-------|
| **BookingManageController** | Gestisce le richieste di prenotazione |
| **BookingService** | Logica di creazione e validazione prenotazioni |
| **BookingMapper** | Converti BookingDTO in entity Booking |
| **BookingRepo** | Accesso ai dati di prenotazione dal database |
| **Database** | Persistenza delle prenotazioni |
