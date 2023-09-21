package it.polito.wa2.server.ticketing

import it.polito.wa2.server.AbstractApplicationTest
import it.polito.wa2.server.products.ProductDTO
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.ticketing.tickets.Statuses
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

class TicketsTests : AbstractApplicationTest() {
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

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create ticket`() {
        createTicket()
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test get ticket by id`() {
        val ticket = createTicket()

        val res = restTemplate.getForEntity("http://localhost:$port/api/tickets/${ticket.id}", TicketDTO::class.java)
        Assertions.assertEquals(HttpStatus.OK, res.statusCode)
        Assertions.assertEquals(ticket, res.body)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test edit ticket`() {
        val ticket = createTicket().copy(priority = 0)


        val res = restTemplate.exchange(
            "http://localhost:$port/api/tickets/${ticket.id}",
            HttpMethod.PUT,
            HttpEntity(ticket),
            TicketDTO::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, res.statusCode)
        Assertions.assertEquals(ticket, res.body)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test delete ticket`() {
        val ticket = createTicket()

        val res = restTemplate.exchange(
            "http://localhost:$port/api/tickets/${ticket.id}",
            HttpMethod.DELETE,
            null,
            TicketDTO::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, res.statusCode)
        Assertions.assertEquals(ticket, res.body)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test add status for ticket`() {
        val ticket = createTicket()
        val status = Statuses.IN_PROGRESS

        val res = restTemplate.postForEntity(
            "http://localhost:$port/api/tickets/${ticket.id}/${status}",
            null,
            TicketDTO::class.java
        )
        Assertions.assertEquals(HttpStatus.CREATED, res.statusCode)
        Assertions.assertNotNull(res.body)

        ticket.statuses.add(status)
        Assertions.assertEquals(ticket, res.body)


    }

}