package it.polito.wa2.server.security.aut

import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.TECHNICIAN
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
// @Observed
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody request: UserRequest?) {
        if (request == null) throw NotValidException("User was malformed")
        authService.createUser(request, listOf(CUSTOMER))
    }

    @PostMapping("/createExpert")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTechnician(@RequestBody request: UserRequest?) {
        if (request == null) throw NotValidException("User was malformed")
        authService.createUser(request, listOf(TECHNICIAN))
    }
}