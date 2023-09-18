package it.polito.wa2.server.security.aut

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.MANAGER
import it.polito.wa2.server.security.TECHNICIAN
import jakarta.transaction.Transactional
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class AuthServiceImpl(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String
) : AuthService {
    override fun createUser(userRequest: UserRequest, roles: List<String>, userDetail: UserDetail) {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può aggiungere utenti
        if (userDetail.role == UserRoles.CUSTOMER && (roles.contains(TECHNICIAN) || roles.contains(MANAGER))) throw UnauthorizedException(
            "Unauthorized"
        ) // un customer può aggiungere solo dei customer

        val password = preparePasswordRepresentation(userRequest.password)
        val user = prepareUserRepresentation(userRequest, password, roles)
        val response = keycloak.realm(realm).users().create(user)

        if (response.status == 403) throw DuplicateException("${response.statusInfo}")
        else if (response.status != 201) throw RuntimeException("${response.statusInfo}")
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
        roles: List<String>
    ): UserRepresentation {
        val newUser = UserRepresentation()
        newUser.username = request.username //request.username.split("@")[0]
        newUser.email = request.email
        newUser.firstName = request.firstName
        newUser.lastName = request.lastName
        newUser.credentials = listOf(cR)
        newUser.isEnabled = true
        newUser.realmRoles = roles
        return newUser
    }
}
