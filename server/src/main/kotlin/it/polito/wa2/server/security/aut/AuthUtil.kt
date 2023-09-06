package it.polito.wa2.server.security.aut

import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.MANAGER
import it.polito.wa2.server.security.TECHNICIAN
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

data class UserRequest(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)

data class UserDetail(val role: UserRoles, val email: String)

fun getUserDetail(user: DefaultOAuth2User?): UserDetail {
    if (user == null) return UserDetail(UserRoles.NO_AUTH, "")
    val email = user.attributes?.get("email") as String
    val tmp = user.attributes?.get("realm_access") as Map<String, List<String>>
    val role = tmp["roles"]?.filter { it -> it == CUSTOMER || it == MANAGER || it == TECHNICIAN }
    if (role.isNullOrEmpty()) return UserDetail(UserRoles.NO_AUTH, email)
    return when (role[0]) {
        CUSTOMER -> UserDetail(UserRoles.CUSTOMER, email)
        MANAGER -> UserDetail(UserRoles.MANAGER, email)
        TECHNICIAN -> UserDetail(UserRoles.TECHNICIAN, email)
        else -> UserDetail(UserRoles.NO_AUTH, email)
    }
}

