```mermaid
flowchart TD
    subgraph BookingService
        BC[createBooking] -->|log.info| BClog["createBooking called with dto={}"]
        BU[updateBooking] -->|log.info| BUlog["updateBooking called for id={} with dto={}"]
        BW[addWaiter] -->|log.info| BWlog["addWaiter called for bookingId={} waiterId={}"]
        BR[removeWaiter] -->|log.info| BRlog["removeWaiter called for bookingId={} waiterId={}"]
        BD[deleteBooking] -->|log.info| BDlog["deleteBooking called for id={}"]
    end
    subgraph DishService
        DC[createDish] -->|log.info| DClog["createDish called dto={}"]
        DU[updateDish] -->|log.info| DUlog["updateDish called for id={} with dto={}"]
        DD[deleteDish] -->|log.info| DDlog["deleteDish id={}"]
    end
    subgraph UserService
        UC[createUser] -->|log.info| UClog["createUser username={}"]
        UU[updateUser] -->|log.info| UUlog["updateUser id={}"]
        UD[deleteUser] -->|log.info| UDlog["deleteUser called for id={}"]
    end
    subgraph GlobalExceptionHandler
        GEH[handleIllegalArgument] -->|log.warn| GEHlog["Errore di validazione: {}"]
        GEH2[handleGenericException] -->|log.error| GEH2log["Errore generico: {}"]
    end
    ```