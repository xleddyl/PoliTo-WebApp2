package it.polito.wa2.server.security.aut

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.security.CUSTOMER
import jakarta.transaction.Transactional
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthServiceImpl(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String
) : AuthService {
    override fun createUser(userRequest: UserRequest, roles: List<String>) {
        val password = preparePasswordRepresentation(userRequest.password)
        val user = prepareUserRepresentation(userRequest, password, listOf(CUSTOMER))
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
        newUser.username = request.username.split("@")[0]
        newUser.email = request.username
        newUser.credentials = listOf(cR)
        newUser.isEnabled = true
        newUser.realmRoles = roles
        return newUser
    }
}
