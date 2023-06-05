package it.polito.wa2.server.profiles

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
// Observed
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): ProfileDTO? {
        // controlla ruolo
        return profileService.getByEmail(email)
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@RequestBody profileDTO: ProfileDTO?): ProfileDTO {
        if (profileDTO == null) throw NotValidException("Profile was malformed")
        return profileService.addProfile(profileDTO)
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(@RequestBody profileDTO: ProfileDTO?, @PathVariable email: String): ProfileDTO {
        if (profileDTO == null) throw NotFoundException("Profile not found")
        if (profileDTO.email != email) throw NotValidException("Profile id and path id doesn't match")

        return profileService.editProfile(profileDTO, email)
    }
}