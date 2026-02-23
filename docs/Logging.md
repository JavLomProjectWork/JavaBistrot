```mermaid
flowchart TD
    subgraph BookingService
        BT[toggleActive] -->|log.info| BTlog["toggleActive called for booking id={}"]
        BC[createBooking] -->|log.info| BClog["createBooking called with dto={}"]
        BU[updateBooking] -->|log.info| BUlog["updateBooking called for id={} with dto={}"]
        BW[addWaiter] -->|log.info| BWlog["addWaiter called for bookingId={} waiterId={}"]
        BR[removeWaiter] -->|log.info| BRlog["removeWaiter called for bookingId={} waiterId={}"]
        BD[deleteBooking] -->|log.info| BDlog["deleteBooking called for id={}"]
    end
    subgraph DishService
        DC[createDish] -->|log.info| DClog["createDish called dto={}"]
        DD[deleteDish] -->|log.info| DDlog["deleteDish id={}"]
        DT[toggleActive] -->|log.info| DTlog["toggleActive dish called id={}"]
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