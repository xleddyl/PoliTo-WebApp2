package it.polito.wa2.server.security.aut

import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.TECHNICIAN
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody request: UserRequest) {
        authService.createUser(request, listOf(CUSTOMER))
    }

    @PostMapping("/createExpert")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTechnician(@Valid @RequestBody request: UserRequest) {
        authService.createUser(request, listOf(TECHNICIAN))
    }
}