# JavaBistrot - UI flows

Documentazione dei diagrammi di processo per i flussi principali dell'applicazione JavaBistrot.

## Indice:

### [Login Process](Login_Process_Diagram.md)
- **Activity Diagram**: Flusso delle attività e punti di controllo
- **Sequence Diagram**: Interazione tra componenti (Controller → Service → Repository → Database)
- **Componenti**: LoginController, UserService, UserRepo, PSWEncoder
- **Punti di fallimento**: Username non trovato, Password errata, Account disabilitato

### [Booking Creation](Booking_Creation_Diagram.md)
- **Activity Diagram**: Flusso di creazione prenotazione
- **Componenti**: BookingManageController, BookingService, BookingMapper, BookingRepo
- **Validazioni**: Dati valori, Conflitti di orario
- **Esiti**: Success o ritorno al form con errore

### [Menu Display](Menu_Display_Diagram.md)
- **Activity Diagram**: Flusso di visualizzazione del menu
- **Componenti**: PublicController, DishService, DishMapper, DishRepo, Thymeleaf
- **Template**: menu.html con CSS (style.css + menu.css)
- **Dati**: Recupero piatti attivi e conversione a DTO

---

## Legenda Colori


| Colore | Significato | UML |
|--------|-------------|-----|
| Verde | Start, Success, DTOs | DTOs |
| Viola | Input, CSS, Enums | Enumerazioni |
| Giallo | Controllers, Decisioni | Controllers |
| Rosso | Repositories, Errori | Repositories |
| Ciano | Services | Services |
| Blu | Entities, Database | Entities |
| Arancione | Mappers | Mappers |

### Flusso Visualizzazione Menu

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

### Colori CSS Utilizzati

- **style.css** - Stili globali della pagina
- **menu.css** - Stili specifici del menu (layout piatti, prezzi, descrizioni)

