package it.polito.wa2.server.security.aut

import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.TECHNICIAN
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody request: UserRequest, @AuthenticationPrincipal user: DefaultOAuth2User?) {
        authService.createUser(request, listOf(CUSTOMER), getUserDetail(user))
    }

    @PostMapping("/createExpert")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTechnician(@Valid @RequestBody request: UserRequest, @AuthenticationPrincipal user: DefaultOAuth2User?) {
        authService.createUser(request, listOf(TECHNICIAN), getUserDetail(user))
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    fun getUserDetails(@AuthenticationPrincipal user: DefaultOAuth2User?): UserDetail {
        return authService.getUserDetails(getUserDetail(user))
    }
}