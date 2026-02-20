# JavaBistrot - Diagrammi di Sequenza e Attività

Documentazione dei diagrammi di processo per i flussi principali dell'applicazione JavaBistrot.

## 📋 Indice dei Diagrammi

### 🔐 [Login Process](Login_Process_Diagram.md)
- **Activity Diagram**: Flusso delle attività e punti di controllo
- **Sequence Diagram**: Interazione tra componenti (Controller → Service → Repository → Database)
- **Componenti**: LoginController, UserService, UserRepo, PSWEncoder
- **Punti di fallimento**: Username non trovato, Password errata, Account disabilitato

### 📝 [Booking Creation](Booking_Creation_Diagram.md)
- **Activity Diagram**: Flusso di creazione prenotazione
- **Componenti**: BookingManageController, BookingService, BookingMapper, BookingRepo
- **Validazioni**: Dati valori, Conflitti di orario
- **Esiti**: Success o ritorno al form con errore

### 📖 [Menu Display](Menu_Display_Diagram.md)
- **Activity Diagram**: Flusso di visualizzazione del menu
- **Componenti**: PublicController, DishService, DishMapper, DishRepo, Thymeleaf
- **Template**: menu.html con CSS (style.css + menu.css)
- **Dati**: Recupero piatti attivi e conversione a DTO

---

## 🎨 Colori Utilizzati

I diagrammi utilizzano lo stesso schema colori dello schema UML del progetto:

| Colore | Significato | UML |
|--------|-------------|-----|
| Verde (#27AE60) | Start, Success, DTOs | DTOs |
| Viola (#9B59B6) | Input, CSS, Enums | Enumerazioni |
| Giallo (#F39C12) | Controllers, Decisioni | Controllers |
| Rosso (#E74C3C) | Repositories, Errori | Repositories |
| Ciano (#1ABC9C) | Services | Services |
| Blu (#4A90E2) | Entities, Database | Entities |
| Arancione (#E67E22) | Mappers | Mappers |

### 📋 Flusso Visualizzazione Menu

| Fase | Componente | Azione |
|------|-----------|--------|
| Request | Browser | Richiesta GET /menu |
| Controller | PublicController | Riceve richiesta GET |
| Service | DishService | Recupera piatti attivi |
| Repository | DishRepo | Query SELECT con filtro active=true |
| Database | Database | Ritorna lista piatti |
| Mapping | DishMapper | Converte entities in DTO |
| View | Thymeleaf | Renderizza template menu.html |
| Styling | CSS | Applica style.css + menu.css |
| Output | Browser | Visualizza menu formattato |

### 🎨 Colori CSS Utilizzati

- **style.css** - Stili globali della pagina
- **menu.css** - Stili specifici del menu (layout piatti, prezzi, descrizioni)

