package it.polito.wa2.server.profiles

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

class ProfilesTests : AbstractApplicationTest() {
    @Test
    fun `test POST on profiles`() {

        val profileDTO = ProfileDTO("test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")

        restTemplate.postForLocation("http://localhost:$port/API/profiles", profileDTO)

        val retrievedProfile = restTemplate.getForObject("http://localhost:$port/API/profiles/test@email.com", ProfileDTO::class.java)

        Assertions.assertEquals(profileDTO, retrievedProfile)
    }

    @Test
    fun `test PUT on profiles`() {

        val profileDTO = ProfileDTO("test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")
        val newProfileDTO = ProfileDTO("test@email.com", "Testing Testing", Roles.CUSTOMER, "222222222")

        restTemplate.postForLocation("http://localhost:$port/API/profiles", profileDTO)
        restTemplate.put("http://localhost:$port/API/profiles/test@email.com", newProfileDTO)


        val retrievedProfile = restTemplate.getForObject("http://localhost:$port/API/profiles/test@email.com", ProfileDTO::class.java)
        Assertions.assertEquals(newProfileDTO, retrievedProfile)
    }
}