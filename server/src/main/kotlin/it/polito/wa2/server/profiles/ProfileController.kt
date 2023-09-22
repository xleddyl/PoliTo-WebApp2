package it.polito.wa2.server.profiles

import it.polito.wa2.server.BadRequestException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.manager.ManagerDTO
import it.polito.wa2.server.profiles.manager.ManagerService
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import it.polito.wa2.server.security.aut.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProfileController(
    private val customerService: CustomerService,
    private val technicianService: TechnicianService,
    private val managerService: ManagerService,
    private val authService: AuthService
) {


    @GetMapping("/profiles")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<Any> {
        val userDetails = authService.getUserDetails(user)
        return customerService.getAll(userDetails) + technicianService.getAll(userDetails) + managerService.getAll(
            userDetails
        )
    }

    @GetMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @AuthenticationPrincipal user: DefaultOAuth2User?): Any? {
        val userDetails = authService.getUserDetails(user)
        return try {
            customerService.getByEmail(email, userDetails)
        } catch (_: Exception) {
            try {
                technicianService.getByEmail(email, userDetails)
            } catch (_: Exception) {
                managerService.getByEmail(email, userDetails)
            }
        }
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@RequestBody profileDTO: Any, @AuthenticationPrincipal user: DefaultOAuth2User?): Any {
        val userDetails = authService.getUserDetails(user)
        return when (profileDTO) {
            is CustomerDTO -> customerService.addProfile(profileDTO, userDetails)
            is TechnicianDTO -> technicianService.addProfile(profileDTO, userDetails)
            is ManagerDTO -> managerService.addProfile(profileDTO, userDetails)
            else -> throw BadRequestException("Bad Request")
        }
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(
        @RequestBody profileDTO: Any,
        @PathVariable email: String,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): Any {
        val userDetails = authService.getUserDetails(user)
        return when (profileDTO) {
            is CustomerDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                customerService.editProfile(profileDTO, userDetails)
            }

            is TechnicianDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                technicianService.editProfile(profileDTO, userDetails)
            }

            is ManagerDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                managerService.editProfile(profileDTO, userDetails)
            }

            else -> throw BadRequestException("Bad Request")
        }
    }
}