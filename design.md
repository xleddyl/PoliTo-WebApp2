# Design

## API

### Products

| method | route           | body       | role    | frontend? |
| ------ | --------------- | ---------- | ------- | :-------: |
| POST   | /products       | productDTO | manager |           |
| GET    | /products       |            | all     |     Y     |
| GET    | /products/{ean} |            | all     |           |

### Profiles

| method | route             | body                                 | role             | frontend? |
| ------ | ----------------- | ------------------------------------ | ---------------- | :-------: |
| GET    | /profiles         |                                      | manager          |     Y     |
| GET    | /profiles/{email} |                                      | all (themselves) |           |
| POST   | /profiles         | CustomerDTO/TechnicianDTO/ManagerDTO | all              |           |
| PUT    | /profiles/{email} | CustomerDTO/TechnicianDTO/ManagerDTO | all              |           |

### Purchase

| method | route              | body        | role            | frontend? |
| ------ | ------------------ | ----------- | --------------- | :-------: |
| GET    | /purchases         |             | manager         |     Y     |
| POST   | /purchases/list    | List(Long)  | manager         |           |
| GET    | /purchases/{email} |             | cutomer/manager |     Y     |
| POST   | /purchases         | purchaseDTO | cutomer/manager |     Y     |
| PUT    | /purchases         | purchaseDTO | cutomer/manager |           |

### Ticket

| method | route                      | body         | role        |   frontend?   |
| ------ | -------------------------- | ------------ | ----------- | :-----------: |
| GET    | /tickets                   |              | da rivedere |       Y       |
| GET    | /tickets/{ticketId}        |              | da rivedere |       Y       |
| POST   | /tickets                   | TicketDTO    | da rivedere |       Y       |
| PUT    | /tickets/{ticketId}/status | TicketStatus | da rivedere |       Y       |
| PUT    | /tickets/{ticketId}        | TicketDTO    | da rivedere | da completare |

### Message

| method | route                                    | body       | role        | frontend? |
| ------ | ---------------------------------------- | ---------- | ----------- | :-------: |
| GET    | /tickets/{ticketId}/messages             |            | da rivedere |     Y     |
| GET    | /tickets/{ticketId}/messages/{messageId} |            | da rivedere |           |
| POST   | /tickets/{ticketId}/messages             | MessageDTO | da rivedere |     Y     |

### Auth

| method | route         | body        | role    | frontend? |
| ------ | ------------- | ----------- | ------- | :-------: |
| GET    | /user         |             | all     |     Y     |
| POST   | /signup       | UserRequest | all     |     Y     |
| POST   | /createExpert | UserRequest | manager |           |
