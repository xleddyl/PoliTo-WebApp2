package it.polito.wa2.server.profiles

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("/API/profiles/{email}")
    fun getByEmail(@PathVariable email: String): ProfileDTO? {
        return profileService.getByEmail(email)
    }
}