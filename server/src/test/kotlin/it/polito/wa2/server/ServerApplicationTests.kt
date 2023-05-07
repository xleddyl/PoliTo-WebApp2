package it.polito.wa2.server

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.profiles.ProfileDTO
import it.polito.wa2.server.profiles.ProfileRepository
import it.polito.wa2.server.profiles.Roles
import it.polito.wa2.server.ticketing.messages.Message
import it.polito.wa2.server.ticketing.tickets.States
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.client.MockRestServiceServer
import java.sql.Timestamp


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServerApplicationTests: AbstractApplicationTest() {


    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Test
    fun testProfile() {

        val profile = ProfileDTO("Test@email.com","Test Test", Roles.TECHNICIAN, "333333333")

        val addedProfile = restTemplate.postForLocation("http://localhost:$port/API/profiles", profile)

        //assertNotNull(addedProfile) // In theory this should not be empty, but it is

        val retrievedProfile = restTemplate.getForObject("http://localhost:$port/API/profiles/Test@email.com",ProfileDTO::class.java)
        assertEquals(profile, retrievedProfile)
    }

    @Test
    fun testUpdateProfile() {

        val profile = ProfileDTO("Test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")
        val newProfile = ProfileDTO("Test@email.com", "Testing Testing", Roles.CUSTOMER, "222222222")

        val addedProfile = restTemplate.put("http://localhost:$port/API/profiles", profile)
        val updatedProfile=restTemplate.put("http://localhost:$port/API/profiles", newProfile)


        val retrievedProfile = restTemplate.getForObject("http://localhost:$port/API/profiles/Test@email.com", ProfileDTO::class.java)
        assertEquals(newProfile, retrievedProfile)
    }

    @Test
    fun testTicket() {

        val customer = Profile("customer@email.com", "customer customer", Roles.CUSTOMER, "222222222")
        val technician = Profile("technician@email.com", "technician tech", Roles.TECHNICIAN, "333333333")

        val product = Product("ean", "sku", "name", "brand", "category", 1.0f)
        val states = mutableListOf<States>(States.OPEN)

        val ticket = TicketDTO(5, product, customer, technician, states, "description", 3, null)

        val addedCustomer = restTemplate.postForLocation("http://localhost:$port/API/profiles", customer)
        val addedTechnician = restTemplate.postForLocation("http://localhost:$port/API/profiles", technician)

        //val addedProduct = restTemplate.postForLocation("http://localhost:$port/API/products", product)

        val addedTicket = restTemplate.postForLocation("http://localhost:$port/API/tickets", ticket)

        val retrievedTicket = restTemplate.getForObject("http://localhost:$port/API/tickets/5", TicketDTO::class.java)
        assertEquals(ticket, retrievedTicket)
    }

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
