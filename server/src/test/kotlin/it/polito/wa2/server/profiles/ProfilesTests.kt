package it.polito.wa2.server.profiles

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

class ProfilesTests : AbstractApplicationTest() {
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create profile`() {
        val profileDTO = ProfileDTO("test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", profileDTO)

        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/api/profiles/test@email.com", ProfileDTO::class.java)

        Assertions.assertEquals(profileDTO, retrievedProfile)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test edit profile`() {
        val profileDTO = ProfileDTO("test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")
        val newProfileDTO = ProfileDTO("test@email.com", "Testing Testing", Roles.CUSTOMER, "222222222")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", profileDTO)
        restTemplate.put("http://localhost:$port/api/profiles/test@email.com", newProfileDTO)

        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/api/profiles/test@email.com", ProfileDTO::class.java)
        Assertions.assertEquals(newProfileDTO, retrievedProfile)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create already existing profile`() {
        val profileDTO = ProfileDTO("test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")
        val newProfileDTO = ProfileDTO("test@email.com", "Mario Rossi", Roles.CUSTOMER, "666")

        restTemplate.postForLocation("http://localhost:$port/api/profiles", profileDTO)
        val res = restTemplate.postForEntity("http://localhost:$port/api/profiles", newProfileDTO, String::class.java)

        Assertions.assertEquals(HttpStatus.CONFLICT, res.statusCode)
        Assertions.assertEquals(true, res.body?.contains("User already exists"))
    }
}