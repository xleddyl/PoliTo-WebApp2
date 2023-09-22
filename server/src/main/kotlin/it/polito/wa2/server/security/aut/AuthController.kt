package it.polito.wa2.server.security.aut

import it.polito.wa2.server.BadRequestException
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import it.polito.wa2.server.security.CUSTOMER_APP_ROLE
import it.polito.wa2.server.security.TECHNICIAN_APP_ROLE
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val technicianService: TechnicianService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody request: UserRequest, @AuthenticationPrincipal user: DefaultOAuth2User?) {
        authService.createUser(request, CUSTOMER_APP_ROLE, authService.getUserDetails(user))
        customerService.addProfile(
            CustomerDTO(
                request.email,
                request.firstName + " " + request.lastName,
                request.phone,
                request.address,
                request.purchasesIDs ?: mutableSetOf()
            ),
            authService.getUserDetails(user)
        )
    }

    @PostMapping("/createExpert")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTechnician(@Valid @RequestBody request: UserRequest, @AuthenticationPrincipal user: DefaultOAuth2User?) {
        authService.createUser(request, TECHNICIAN_APP_ROLE, authService.getUserDetails(user))
        technicianService.addProfile(
            TechnicianDTO(
                request.email,
                request.firstName + " " + request.lastName,
                request.phone,
                request.specialization ?: throw BadRequestException("Bad Request"),
                request.managerID ?: throw BadRequestException("Bad Request"),
                request.ticketsIDs ?: mutableSetOf()
            ),
            authService.getUserDetails(user)
        )
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    fun getUserDetails(@AuthenticationPrincipal user: DefaultOAuth2User?): UserDetail {
        return authService.getUserDetails(user)
    }
}