package it.polito.wa2.server.profiles

import it.polito.wa2.server.AbstractApplicationTest
import it.polito.wa2.server.profiles.customer.Customer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

class ProfilesTests : AbstractApplicationTest() {
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create profile`() {
        val customerDTO = Customer("test@email.com", "Test Test", "333333333", "aaaa")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", customerDTO)

        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/api/profiles/test@email.com", Profile::class.java)

        Assertions.assertEquals(customerDTO, retrievedProfile)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test edit profile`() {
        val customerDTO = Customer("test@email.com", "Test Test", "333333333", "aaaa")
        val newCustomerDTO = Customer("test@email.com", "Testing Testing", "222222222", "bbbb")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", customerDTO)
        restTemplate.put("http://localhost:$port/api/profiles/test@email.com", newCustomerDTO)

        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/api/profiles/test@email.com", Profile::class.java)
        Assertions.assertEquals(newCustomerDTO, retrievedProfile)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create already existing profile`() {
        val customerDTO = Customer("test@email.com", "Test Test", "333333333", "aaaa")
        val newCustomerDTO = Customer("test@email.com", "Testing Testing", "222222222", "bbbb")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", customerDTO)
        val res = restTemplate.postForEntity("http://localhost:$port/api/profiles", newCustomerDTO, String::class.java)

        Assertions.assertEquals(HttpStatus.CONFLICT, res.statusCode)
        Assertions.assertEquals(true, res.body?.contains("User already exists"))
    }
}