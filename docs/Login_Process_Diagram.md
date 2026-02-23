# Login Process - Activity Diagram

```mermaid
graph TD
    Start([Utente accede a /login]) --> Input["Inserisce username e password"]
    Input --> Submit["Submit form POST /login"]
    Submit --> CheckUser{"Username<br/>esiste?"}
    
    CheckUser -->|No| ErrorUser["Errore:<br/>Utente non trovato"]
    ErrorUser --> DisplayError["Mostra messaggio d'errore"]
    DisplayError --> End1([Torna a /login])
    
    CheckUser -->|Sì| RetrieveUser["Recupera User dal DB"]
    RetrieveUser --> CheckPassword{"Password<br/>corretta?"}
    
    CheckPassword -->|No| ErrorPassword["Errore:<br/>Password sbagliata"]
    ErrorPassword --> DisplayError2["Mostra messaggio d'errore"]
    DisplayError2 --> End2([Torna a /login])
    
    CheckPassword -->|Sì| CheckRole{"Ruolo utente<br/>valido?"}
    
    CheckRole -->|No| ErrorRole["Errore:<br/>Accesso negato"]
    ErrorRole --> DisplayError3["Account disabilitato"]
    DisplayError3 --> End3([Torna a /login])
    
    CheckRole -->|Sì| CreateSecurity["Crea SecurityContext"]
    CreateSecurity --> SetSession["Salva sessione"]
    SetSession --> Redirect["Redirect a /dashboard"]
    Redirect --> End4(["Login completato"])
    
    style Start stroke:#27AE60,stroke-width:2px
    style Input stroke:#9B59B6,stroke-width:2px
    style Submit stroke:#9B59B6,stroke-width:2px
    style CheckUser stroke:#F39C12,stroke-width:2px
    style CheckPassword stroke:#F39C12,stroke-width:2px
    style CheckRole stroke:#F39C12,stroke-width:2px
    style ErrorUser stroke:#E74C3C,stroke-width:2px
    style ErrorPassword stroke:#E74C3C,stroke-width:2px
    style ErrorRole stroke:#E74C3C,stroke-width:2px
    style DisplayError stroke:#E74C3C,stroke-width:2px
    style DisplayError2 stroke:#E74C3C,stroke-width:2px
    style DisplayError3 stroke:#E74C3C,stroke-width:2px
    style CreateSecurity stroke:#1ABC9C,stroke-width:2px
    style SetSession stroke:#1ABC9C,stroke-width:2px
    style Redirect stroke:#1ABC9C,stroke-width:2px
    style End1 stroke:#E74C3C,stroke-width:2px
    style End2 stroke:#E74C3C,stroke-width:2px
    style End3 stroke:#E74C3C,stroke-width:2px
    style End4 stroke:#27AE60,stroke-width:2px
```

## Flusso delle Attività

| Fase | Attività | Descrizione |
|------|----------|-------------|
| **Input** | Inserimento credenziali | Utente compila form con username e password |
| **Validazione Username** | Verifica esistenza | Controlla se l'utente esiste nel DB |
| **Validazione Password** | Verifica correttezza | Usa PSWEncoder per verificare la password |
| **Validazione Ruolo** | Controlla permessi | Verifica che l'account sia attivo e abilitato |
| **Creazione Contesto** | SecurityContext | Crea sessione di autenticazione |
| **Salvataggio Sessione** | Session Management | Memorizza i dati di sessione |
| **Reindirizzamento** | Navigation | Invia l'utente al dashboard |

## Punti di Fallimento

1. **Username non trovato** → Torna a /login con errore
2. **Password incorretta** → Torna a /login con errore
3. **Account disabilitato** → Accesso negato anche se credenziali corrette

---

## Login Process - Sequence Diagram

```mermaid
sequenceDiagram
    participant Browser as Browser
    participant LoginCtrl as LoginController
    participant UserSvc as UserService
    participant UserRepo as UserRepo
    participant PSWEncoder as PSWEncoder
    participant DB as Database
    
    Browser->>LoginCtrl: POST /login<br/>(username, password)
    activate LoginCtrl
    
    LoginCtrl->>UserSvc: createAuthentication(username, password)
    activate UserSvc
    
    UserSvc->>UserRepo: findByUsername(username)
    activate UserRepo
    
    UserRepo->>DB: SELECT * FROM users<br/>WHERE username = ?
    activate DB
    DB-->>UserRepo: User record
    deactivate DB
    
    UserRepo-->>UserSvc: Optional~User~
    deactivate UserRepo
    
    alt User Found
        activate UserSvc
        UserSvc->>PSWEncoder: matches(password, hashedPassword)
        activate PSWEncoder
        
        alt Password Matches
            activate PSWEncoder
            PSWEncoder-->>UserSvc: true
            deactivate PSWEncoder
            
            activate UserSvc
            UserSvc->>UserSvc: Create SecurityContext
            UserSvc-->>LoginCtrl: Authentication successful
            deactivate UserSvc
            
            LoginCtrl->>Browser: Redirect to /dashboard
            
        else Password Incorrect
            PSWEncoder-->>UserSvc: false
            deactivate PSWEncoder
            
            UserSvc-->>LoginCtrl: AuthenticationException
            deactivate UserSvc
            
            LoginCtrl->>Browser: 401 Unauthorized<br/>Invalid credentials
        end
        
    else User Not Found
        UserSvc-->>LoginCtrl: AuthenticationException
        deactivate UserSvc
        
        LoginCtrl->>Browser: 401 Unauthorized<br/>Invalid credentials
    end
    
    deactivate LoginCtrl
```

## Descrizione Flusso

1. **Browser** → Invia credenziali (username, password) al LoginController
2. **LoginController** → Delega a UserService il processo di autenticazione
3. **UserService** → Richiede l'utente al database tramite UserRepo
4. **UserRepo** → Esegue query sul database
5. **PSWEncoder** → Verifica che la password inserita corrisponda all'hash memorizzato
6. **Esito**:
   - ✅ **Successo**: Crea SecurityContext e reindirizza a /dashboard
   - ❌ **Fallimento**: Ritorna 401 Unauthorized

## Componenti Coinvolti

| Componente | Ruolo |
|-----------|-------|
| **LoginController** | Gestisce le richieste di login dal browser |
| **UserService** | Logica di autenticazione e validazione |
| **UserRepo** | Accesso ai dati utente dal database |
| **PSWEncoder** | Verifica le password usando BCrypt |
| **SecurityConfig** | Configurazione di Spring Security (non mostrato) |
