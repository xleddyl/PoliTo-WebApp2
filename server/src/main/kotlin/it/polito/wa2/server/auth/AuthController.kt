package it.polito.wa2.server.auth

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotValidException
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.ws.rs.core.Response

@RestController
@RequestMapping("/api")
@Observed
class AuthController(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String
) {
    inner class UserRequest(
        val username: String,
        val password: String
    )

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: UserRequest?): Response {
        if (request == null) throw NotValidException("Product was malformed")
        val password = preparePasswordRepresentation(request.password)
        // val role = keycloak.realm(realm).roles().get("app_customer").toRepresentation()
        val user = prepareUserRepresentation(request, password)
        println(user)

        return keycloak.realm(realm).users().create(user)
        // keycloak.realm(realm).users().get(user.id).roles().realmLevel().add(listOf(role))
    }

    private fun preparePasswordRepresentation(
        password: String
    ): CredentialRepresentation {
        val cR = CredentialRepresentation()
        cR.isTemporary = false
        cR.type = CredentialRepresentation.PASSWORD
        cR.value = password
        return cR
    }

    private fun prepareUserRepresentation(
        request: UserRequest,
        cR: CredentialRepresentation,
    ): UserRepresentation {
        val newUser = UserRepresentation()
        newUser.username = request.username
        newUser.credentials = listOf(cR)
        newUser.isEnabled = true
        newUser.realmRoles.add("app_customer")
        return newUser
    }
}