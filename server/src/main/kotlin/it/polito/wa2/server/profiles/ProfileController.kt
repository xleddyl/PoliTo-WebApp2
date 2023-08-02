package it.polito.wa2.server.profiles

import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ProfileController(
    private val customerService: CustomerService,
    private val technicianService: TechnicianService
) {

    @GetMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getByEmail(@PathVariable email: String): Any {
        return (customerService.getByEmail(email) ?: technicianService.getByEmail(email)) as Any
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@Valid @RequestBody profileDTO: CustomerDTO): CustomerDTO {
        return customerService.addProfile(profileDTO)
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProfile(@Valid @RequestBody profileDTO: TechnicianDTO): TechnicianDTO {
        return technicianService.addProfile(profileDTO)
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(@Valid @RequestBody profileDTO: CustomerDTO, @PathVariable email: String): CustomerDTO {
        if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
        return customerService.editProfile(profileDTO, email)
    }

    @PutMapping("/profiles/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editProfile(@Valid @RequestBody profileDTO: TechnicianDTO, @PathVariable email: String): TechnicianDTO {
        if (profileDTO.email != email) throw NotValidException("Profile id and path id don't match")
        return technicianService.editProfile(profileDTO, email)
    }
}