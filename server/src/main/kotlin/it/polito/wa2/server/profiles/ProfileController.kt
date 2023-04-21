package it.polito.wa2.server.profiles

import it.polito.wa2.server.DTONotValidException
import it.polito.wa2.server.ProfileNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("/API/profiles/{email}")
    fun getByEmail(@PathVariable email: String): ProfileDTO? {
        val profile = profileService.getByEmail(email)
        if (profile == null) {
            throw ProfileNotFoundException("No user associated with this email address")
        }
        return profile
    }

    @PostMapping("/API/profiles")
    fun addProfile(@RequestBody profile: ProfileDTO?) {
        if (profile == null) {
            throw DTONotValidException("Invalid Profile DTO for the current profile")
        }
        profileService.addProfile(profile)
    }

    @PutMapping("/API/profiles/{email}")
    fun editProfile(@RequestBody profile: ProfileDTO?, @PathVariable email: String) {
        if (profile == null)
            throw  ProfileNotFoundException("No user associated with this email address")
        if (profile.email != email)
            throw DTONotValidException("Invalid Profile DTO for the current profile")

        profileService.editProfile(profile, email)
    }
}