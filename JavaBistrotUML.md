# JavaBistrot - UML Class Diagram

```mermaid
classDiagram
    class User {
        -Long id
        -String username
        -String password
        -Role role
        -Set~Booking~ bookings
        -Boolean active
        +getAuthorities() Collection
        +getPassword() String
        +getUsername() String
        +isAccountNonExpired() boolean
        +isAccountNonLocked() boolean
        +isCredentialsNonExpired() boolean
        +isEnabled() boolean
    }

    class Role {
        <<enumeration>>
        WAITER
        MAITRE
    }

    class Booking {
        -Long id
        -String customerName
        -String email
        -String phoneNumber
        -Integer numberOfGuests
        -LocalDateTime bookingDateTime
        -String notes
        -Set~User~ assignedWaiters
        +getId() Long
        +setCustomerName(String) void
        +setEmail(String) void
        +setPhoneNumber(String) void
    }

    class UserDTO {
        <<record>>
        +Long id
        +String username
        +Role role
        +Boolean active
    }

    class BookingDTO {
        <<record>>
        +Long id
        +String customerName
        +String email
        +String phoneNumber
        +Integer numberOfGuests
        +LocalDateTime bookingDateTime
        +Set~Long~ assignedWaiterIds
        +String notes
    }

    class UserMapper {
        <<Component>>
        +toEntity(UserDTO) User
        +toDto(User) UserDTO
    }

    class BookingMapper {
        <<Component>>
        +toEntity(BookingDTO) Booking
        +toDto(Booking) BookingDTO
    }

    class UserRepo {
        <<interface>>
        <<JpaRepository>>
        +findByUsername(String) Optional~User~
    }

    class BookingRepo {
        <<interface>>
        <<JpaRepository>>
        +findByBookingDateTimeBetween(LocalDateTime, LocalDateTime) List~Booking~
        +findByCustomerNameContainingIgnoreCase(String) List~Booking~
        +findByPhoneNumber(String) List~Booking~
        +findByEmail(String) List~Booking~
    }

    %% Relationships
    User "1" -- "*" Booking: assignedWaiters
    User --> Role: uses
    
    UserDTO --|> User: maps to
    BookingDTO --|> Booking: maps to
    
    UserMapper --> User: converts
    UserMapper --> UserDTO: converts
    
    BookingMapper --> Booking: converts
    BookingMapper --> BookingDTO: converts
    
    UserRepo --> User: manages
    BookingRepo --> Booking: manages
```

## Descrizione dei Componenti

### Entità (Entities)
- **User**: Rappresenta un utente del sistema (cameriere o maître). Implementa `UserDetails` per la sicurezza Spring.
- **Booking**: Rappresenta una prenotazione al ristorante.
- **Role**: Enum che definisce i ruoli disponibili (WAITER, MAITRE).

### DTO (Data Transfer Objects)
- **UserDTO**: Record per trasferire dati utente tra client e server.
- **BookingDTO**: Record per trasferire dati di prenotazione tra client e server.

### Mapper
- **UserMapper**: Converte tra `User` e `UserDTO`.
- **BookingMapper**: Converte tra `Booking` e `BookingDTO`.

### Repository
- **UserRepo**: Interfaccia JpaRepository per operazioni CRUD su User.
- **BookingRepo**: Interfaccia JpaRepository per operazioni CRUD su Booking con query personalizzate.

### Relazioni
- Una **Booking** è assegnata a **User** (relazione ManyToMany)
- **User** utilizza **Role** come enum
- I Mapper convertono tra Entity e DTO
- I Repository gestiscono l'accesso ai dati
