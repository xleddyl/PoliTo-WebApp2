# Design

## API

### Products

| method | route           | body       | frontend? |
| ------ | --------------- | ---------- | :-------: |
| POST   | /products       | productDTO |           |
| GET    | /products       |            |     Y     |
| GET    | /products/{ean} |            |           |

### Profiles

| method | route             | body                                 |   frontend?   |
| ------ | ----------------- | ------------------------------------ | :-----------: |
| GET    | /profiles         |                                      |       Y       |
| GET    | /profiles/{email} |                                      |               |
| POST   | /profiles         | CustomerDTO/TechnicianDTO/ManagerDTO | con la signup |
| PUT    | /profiles/{email} | CustomerDTO/TechnicianDTO/ManagerDTO |               |

### Purchase

| method | route              | body        | frontend? |
| ------ | ------------------ | ----------- | :-------: |
| GET    | /purchases         |             |     Y     |
| POST   | /purchases/list    | List(Long)  |           |
| GET    | /purchases/{email} |             |     Y     |
| POST   | /purchases         | purchaseDTO |     Y     |
| PUT    | /purchases         | purchaseDTO |           |

### Ticket

| method | route                      | body         | frontend? |
| ------ | -------------------------- | ------------ | :-------: |
| GET    | /tickets                   |              |     Y     |
| GET    | /tickets/{ticketId}        |              |     Y     |
| POST   | /tickets                   | TicketDTO    |     Y     |
| PUT    | /tickets/{ticketId}/status | TicketStatus |     Y     |
| PUT    | /tickets/{ticketId}        | TicketDTO    |     Y     |

### Message

| method | route                                    | body       | frontend? |
| ------ | ---------------------------------------- | ---------- | :-------: |
| GET    | /tickets/{ticketId}/messages             |            |     Y     |
| GET    | /tickets/{ticketId}/messages/{messageId} |            |           |
| POST   | /tickets/{ticketId}/messages             | MessageDTO |     Y     |

### Auth

| method | route         | body        | frontend? |
| ------ | ------------- | ----------- | :-------: |
| GET    | /user         |             |     Y     |
| POST   | /signup       | UserRequest |     Y     |
| POST   | /createExpert | UserRequest |     Y     |
