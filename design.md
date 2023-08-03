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

| method | route                       |    body     | description                                | implemented |                          role                           |
|:-------|:----------------------------|:-----------:|:-------------------------------------------|:-----------:|:-------------------------------------------------------:|
| POST   | /products                   | ProductDTO  | add a product                              |      Y      |                         MANAGER                         |
| GET    | /products                   |      -      | get all products                           |      Y      |                         MANAGER                         |
| GET    | /products/{ean}             |      -      | get product by ean                         |      Y      |                         MANAGER                         |
| ------ | --------------------------- | ----------- | ------------------------------------------ | ----------- | ------------------------------------------------------- |
| GET    | /profiles/                  |      -      | get all profiles                           |      Y      |                      MANAGER (all)                      |
| GET    | /profiles/{email}           |      -      | get profile by email                       |      Y      | MANAGER (all), CUSTOMER (himself), TECHNICIAN (himself) |
| POST   | /profiles                   | ProfileDTO  | create profile                             |      Y      |              MANAGER, CUSTOMER, TECHNICIAN              |
| PUT    | /profiles/{email}           | ProfileDTO  | edit profile                               |      Y      |    MANAGER, CUSTOMER (himself), TECHNICIAN (himself)    |
| ------ | --------------------------- | ----------- | ------------------------------------------ | ----------- | ------------------------------------------------------- |
| GET    | /tickets                    |      -      | get all tickets                            |      Y      |                         MANAGER                         |
| GET    | /tickets/{id}               |      -      | get ticket by id                           |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| POST   | /tickets                    |  TicketDTO  | create ticket                              |      Y      |                    MANAGER, CUSTOMER                    |
| POST   | /tickets/{id}/{status}      |      -      | edit ticket state                          |      Y      |                   MANAGER, TECHNICIAN                   |
| PUT    | /tickets/{id}               |  TicketDTO  | edit ticket                                |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| DELETE | /tickets/{id}               |      -      | delete ticket                              |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| ------ | --------------------------- | ----------- | ------------------------------------------ | ----------- | ------------------------------------------------------- |
| GET    | /tickets/{id}/messages      |      -      | get all the messages for a specific ticket |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| GET    | /tickets/{id}/messages/{id} |      -      | get message by id for a specific ticket    |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| POST   | /tickets/{id}/messages      | MessageDTO  | create message for a specific ticket       |      Y      |  MANAGER(all), TECHNICIAN(himself), CUSTOMER (himself)  |
| ------ | --------------------------- | ----------- | ------------------------------------------ | ----------- | ------------------------------------------------------- |
| POST   | /signup                     | UserRequest | create a new user in keycloak              |      Y      |                                                         |
| POST   | /createExpert               | UserRequest | create a new technician                    |      Y      |                         MANAGER                         |