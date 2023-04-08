package it.polito.wa2.server.profiles

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
            TODO("ERROR -> email does not exists")
        }
        return profile
    }

    @PostMapping("/API/profiles")
    fun addProfile(@RequestBody profile: ProfileDTO?) {
        if (profile == null) {
            TODO("ERROR -> invalid profileDTO")
        }
        profileService.addProfile(profile)
    }

    @PutMapping("/API/profiles/{email}")
    fun editProfile(@RequestBody profile: ProfileDTO?, @PathVariable email: String) {
        if (profile == null || profile.email != email) {
            TODO("ERROR -> invalid profileDTO || emails do not match")
        }
        profileService.editProfile(profile, email)
    }
}