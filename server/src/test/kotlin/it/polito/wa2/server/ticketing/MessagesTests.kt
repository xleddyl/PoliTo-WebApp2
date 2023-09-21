package it.polito.wa2.server.ticketing

import it.polito.wa2.server.AbstractApplicationTest
import it.polito.wa2.server.products.ProductDTO
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.ticketing.messages.MessageDTO
import it.polito.wa2.server.ticketing.tickets.Statuses
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.sql.Timestamp

class MessagesTests : AbstractApplicationTest() {
    fun createTicket(): TicketDTO {
        val customerDTO = CustomerDTO("customer@email.com", "customer customer", "222222222", "aaaa")
        val technicianDTO = TechnicianDTO("technician@email.com", "technician tech", "333333333", "bbbb")
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)
        val statuses = mutableListOf(Statuses.OPEN)
        val ticketDTO = TicketDTO(null, productDTO, customerDTO, technicianDTO, statuses, "description", 3, null)

        restTemplate.postForLocation("http://localhost:$port/api/profiles", customerDTO)
        restTemplate.postForLocation("http://localhost:$port/api/profiles", technicianDTO)
        restTemplate.postForLocation("http://localhost:$port/api/products", productDTO)

        val res = restTemplate.postForEntity("http://localhost:$port/api/tickets", ticketDTO, TicketDTO::class.java)
        Assertions.assertEquals(HttpStatus.CREATED, res.statusCode)
        Assertions.assertNotNull(res.body)

        val ticket = ticketDTO.copy(id = res.body?.id, messages = res.body?.messages)
        Assertions.assertEquals(ticket, res.body)

        return ticket
    }

    fun createMessage(): MessageDTO {
        val ticket = createTicket()

        val timestamp = Timestamp.valueOf("2023-05-20 12:23:50")
        val messageDTO = MessageDTO(null, ticket.id!!, true, timestamp, "", "Test Message", false)

        val res = restTemplate.postForEntity(
            "http://localhost:$port/api/tickets/${ticket.id}/messages",
            messageDTO,
            MessageDTO::class.java
        )
        Assertions.assertEquals(HttpStatus.CREATED, res.statusCode)
        Assertions.assertNotNull(res.body)

        val message = messageDTO.copy(id = res.body?.id)
        Assertions.assertEquals(message, res.body)

        return message
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test add message`() {
        createMessage()
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test get message`() {
        val messageDTO = createMessage()

        val res = restTemplate.getForEntity(
            "http://localhost:$port/api/tickets/${messageDTO.ticket}/messages/${messageDTO.id}",
            MessageDTO::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, res.statusCode)
        Assertions.assertEquals(messageDTO, res.body)

    }
}