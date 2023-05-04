package it.polito.wa2.server.profiles

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.web.bind.annotation.*

@RestController
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("/API/profiles/{email}")
    fun getByEmail(@PathVariable email: String): ProfileDTO? {
        return profileService.getByEmail(email) ?: throw NotFoundException("Profile not found")
    }

    @PostMapping("/API/profiles")
    fun addProfile(@RequestBody profile: ProfileDTO?) {
        if (profile == null) throw NotValidException("Profile was malformed")
        profileService.addProfile(profile)
    }

    @PutMapping("/API/profiles/{email}")
    fun editProfile(@RequestBody profile: ProfileDTO?, @PathVariable email: String) {
        if (profile == null) throw NotFoundException("Profile not found")
        if (profile.email != email) throw NotValidException("Profile id and path id doesn't match")

        profileService.editProfile(profile, email)
    }
}