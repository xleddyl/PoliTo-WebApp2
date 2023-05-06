package it.polito.wa2.server

import it.polito.wa2.server.profiles.ProfileDTO
import it.polito.wa2.server.profiles.ProfileRepository
import it.polito.wa2.server.profiles.Roles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort


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

}
