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
    fun addProfile(@RequestBody profileDTO: ProfileDTO?) {
        if (profileDTO == null) throw NotValidException("Profile was malformed")
        profileService.addProfile(profileDTO)
    }

    @PutMapping("/API/profiles/{email}")
    fun editProfile(@RequestBody profileDTO: ProfileDTO?, @PathVariable email: String) {
        if (profileDTO == null) throw NotFoundException("Profile not found")
        if (profileDTO.email != email) throw NotValidException("Profile id and path id doesn't match")

        profileService.editProfile(profileDTO, email)
    }
}