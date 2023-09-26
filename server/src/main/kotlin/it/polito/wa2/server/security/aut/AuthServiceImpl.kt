package it.polito.wa2.server.security.aut

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.*
import jakarta.transaction.Transactional
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class AuthServiceImpl(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String,
    private val log: Logger
) : AuthService {
    override fun createUser(userRequest: UserRequest, role: String, userDetail: UserDetail) {
        if ((role == TECHNICIAN_APP_ROLE || role == MANAGER_APP_ROLE) && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException(
            "Unauthorized"
        )

        val password = preparePasswordRepresentation(userRequest.password)
        val roleRepresentation = keycloak.realm(realm).roles().get(role).toRepresentation()
        val user = prepareUserRepresentation(userRequest, password)
        val response = keycloak.realm(realm).users().create(user)

        if (response.status == 403) throw DuplicateException("${response.statusInfo}")
        else if (response.status != 201) throw RuntimeException("${response.statusInfo}")

        val userID = keycloak.realm(realm).users().search(user.username)[0].id
        val keycloakUser = keycloak.realm(realm).users().get(userID)
        keycloakUser.roles().realmLevel().add(mutableListOf(roleRepresentation))
    }

    override fun getUserDetails(user: DefaultOAuth2User?): UserDetail {
        if (user == null) return UserDetail(UserRoles.NO_AUTH, "")
        val email = user.attributes?.get("email") as String
        val tmp = user.attributes?.get("realm_access") as Map<String, List<String>>
        val role =
            tmp["roles"]?.filter { it == CUSTOMER_APP_ROLE || it == MANAGER_APP_ROLE || it == TECHNICIAN_APP_ROLE }
        if (role.isNullOrEmpty()) return UserDetail(UserRoles.NO_AUTH, email)
        return when (role[0]) {
            CUSTOMER_APP_ROLE -> UserDetail(UserRoles.CUSTOMER, email)
            MANAGER_APP_ROLE -> UserDetail(UserRoles.MANAGER, email)
            TECHNICIAN_APP_ROLE -> UserDetail(UserRoles.TECHNICIAN, email)
            else -> UserDetail(UserRoles.NO_AUTH, email)
        }
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
        cR: CredentialRepresentation
    ): UserRepresentation {
        val newUser = UserRepresentation()
        newUser.username = request.username //request.username.split("@")[0]
        newUser.email = request.email
        newUser.firstName = request.firstName
        newUser.lastName = request.lastName
        newUser.credentials = listOf(cR)
        newUser.isEnabled = true
        return newUser
    }
}
