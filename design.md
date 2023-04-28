# Design

## Entities

```pseudo
enum class States {
    OPEN, CLOSED, IN_PROGRESS, RESOLVED, REOPEN
}

Ticket (
    @Id @GeneratedValue id: String
    product: String (product ean)
    customer: String (customer email)
    technician: String (technician email)
    status: mutableListOf<States>()
    description: String
    priority: Int
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    messages: mutableSetOf<Message>()
)

Message (
    @Id @GeneratedValue id: String
    @ManyToOne
    ticket: String
    fromCustomer: Bool
    timestamp: Date,
    attachment: Byte[]
    content: String
)
```

## Api

- root: /API

| method | route                       |    body    | description                                | implemented |
|:-------|:----------------------------|:----------:|:-------------------------------------------|:-----------:|
| GET    | /products                   |     -      | get all products                           |      Y      |
| GET    | /products/{ean}             |     -      | get product by ean                         |      Y      |
| GET    | /profiles/{email}           |     -      | get profile by email                       |      Y      |
| POST   | /profiles                   | ProfileDTO | create profile                             |      Y      |
| PUT    | /profiles/{email}           | ProfileDTO | edit profile                               |      Y      |
| GET    | /tickets                    |     -      | get all tickets                            |      Y      |
| GET    | /tickets/{id}               |     -      | get ticket by id                           |      N      |
| POST   | /tickets/{id}               | TicketDTO  | create ticket                              |      N      |
| PUT    | /tickets/{id}               | TicketDTO  | edit ticket                                |      N      |
| GET    | /tickets/{id}/messages      |     -      | get all the messages for a specific ticket |      N      |
| GET    | /tickets/{id}/messages/{id} |     -      | get message by id for a specific ticket    |      N      |
| POST   | /tickets/{id}/messages      | MessageDTO | create message for a specific ticket       |      N      |