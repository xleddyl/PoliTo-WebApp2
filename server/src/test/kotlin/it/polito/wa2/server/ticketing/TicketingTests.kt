package it.polito.wa2.server.ticketing

import it.polito.wa2.server.AbstractApplicationTest
import it.polito.wa2.server.products.Product
import it.polito.wa2.server.products.ProductDTO
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.profiles.ProfileDTO
import it.polito.wa2.server.profiles.Roles
import it.polito.wa2.server.ticketing.tickets.States
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

class TicketingTests : AbstractApplicationTest() {
    @Test
    fun testTicket() {

        val customerDTO = ProfileDTO("customer@email.com", "customer customer", Roles.CUSTOMER, "222222222")
        val technicianDTO = ProfileDTO("technician@email.com", "technician tech", Roles.TECHNICIAN, "333333333")
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)
        val states = mutableListOf<States>(States.OPEN)
        val ticketId: Long = 1         // ticket id is automatically assigned and autoincremented
        val ticketDTO = TicketDTO(ticketId, productDTO, customerDTO, technicianDTO, states, "description", 3, null)

        restTemplate.postForLocation("http://localhost:$port/API/profiles", customerDTO)
        restTemplate.postForLocation("http://localhost:$port/API/profiles", technicianDTO)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)
        restTemplate.postForLocation("http://localhost:$port/API/tickets", ticketDTO)


        val retrievedTicket = restTemplate.getForObject("http://localhost:$port/API/tickets/$ticketId", TicketDTO::class.java)

        Assertions.assertEquals(ticketDTO, retrievedTicket)
    }

    /*
    @Test
    fun testEditTicket() {
        val technician = Profile("customer@email.com", "customer customer", Roles.CUSTOMER, "222222222")
        val customer = Profile("technician@email.com", "technician tech", Roles.TECHNICIAN, "333333333")

        val product = Product("ean", "sku", "name", "brand", "category", 1.0f)
        val states = mutableListOf<States>(States.IN_PROGRESS)

        val ticket = TicketDTO(5, product, customer, technician, states, "description", 3, null)

        val addedTicket = restTemplate.put("http://localhost:$port/API/tickets", ticket)

        val retrievedTicket = restTemplate.getForObject("http://localhost:$port/API/tickets/5", TicketDTO::class.java)
        assertEquals(ticket, retrievedTicket)
    }

     */

    /*
        @Test
        fun testMessage() {
            val technician = Profile("customer@email.com", "customer customer", Roles.CUSTOMER, "222222222")
            val customer = Profile("technician@email.com", "technician tech", Roles.TECHNICIAN, "333333333")

            val product = Product("ean", "sku", "name", "brand", "category", 1.0f)
            val states = mutableListOf<States>(States.IN_PROGRESS)

            val ticket = TicketDTO(5, product, customer, technician, states, "description", 3, null)

            val addedTicket = restTemplate.put("http://localhost:$port/API/tickets", ticket)

            val message = Message()
        }
    */
}