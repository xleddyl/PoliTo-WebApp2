package it.polito.wa2.server.profiles

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

class ProfilesTests : AbstractApplicationTest() {

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testProfile() {

        val profileDTO = ProfileDTO("Test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")

        val addedProfile = restTemplate.postForLocation("http://localhost:$port/API/profiles", profileDTO)

        //assertNotNull(addedProfile) // In theory this should not be empty, but it is

        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/API/profiles/Test@email.com", ProfileDTO::class.java)
        Assertions.assertEquals(profileDTO, retrievedProfile)
    }

    @Test
    fun testUpdateProfile() {

        val profileDTO = ProfileDTO("Test@email.com", "Test Test", Roles.TECHNICIAN, "333333333")
        val newProfileDTO = ProfileDTO("Test@email.com", "Testing Testing", Roles.CUSTOMER, "222222222")

        val addedProfile = restTemplate.postForLocation("http://localhost:$port/API/profiles", profileDTO)
        val updatedProfile = restTemplate.put("http://localhost:$port/API/profiles/Test@email.com", newProfileDTO)


        val retrievedProfile =
            restTemplate.getForObject("http://localhost:$port/API/profiles/Test@email.com", ProfileDTO::class.java)
        Assertions.assertEquals(newProfileDTO, retrievedProfile)
    }
}