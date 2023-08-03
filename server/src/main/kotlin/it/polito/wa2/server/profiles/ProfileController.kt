package it.polito.wa2.server.profiles

import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import it.polito.wa2.server.profiles.manager.ManagerService
import it.polito.wa2.server.security.aut.UserRole
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.ws.rs.BadRequestException

@RestController
@RequestMapping("/api")
class ProfileController(
    private val customerService: CustomerService,
    private val technicianService: TechnicianService,
    private val managerService: ManagerService
) {
    @GetMapping("/profiles")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<Any> {
        return customerService.getAll() + technicianService.getAll() + managerService.getAll()
    }

    @GetMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String, @UserRole userRole: UserRoles): Any? {
        return userRole
        // return customerService.getByEmail(email) ?: technicianService.getByEmail(email)
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@RequestBody profileDTO: Any): Any {
        return when (profileDTO) {
            is CustomerDTO -> customerService.addProfile(profileDTO)
            is TechnicianDTO -> technicianService.addProfile(profileDTO)
            else -> throw BadRequestException()
        }
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(@RequestBody profileDTO: Any, @PathVariable email: String): Any {
        return when (profileDTO) {
            is CustomerDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                customerService.editProfile(profileDTO, email)
            }
            is TechnicianDTO -> {
                if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
                technicianService.editProfile(profileDTO, email)
            }
            else -> throw BadRequestException()
        }
    }
}