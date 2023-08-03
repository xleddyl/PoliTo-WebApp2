package it.polito.wa2.server.security.aut

import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.MANAGER
import it.polito.wa2.server.security.TECHNICIAN
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Bean
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Component
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserRole

@Aspect
@Component
class RoleAspect {
    @Around("@annotation(UserRole)")
    fun getUserRole(@AuthenticationPrincipal user: DefaultOAuth2User): UserRoles {
        val tmp = user.attributes?.get("realm_access") as Map<String, List<String>>
        val role = tmp["roles"]?.filter { it -> it == CUSTOMER || it == MANAGER || it == TECHNICIAN }
        if (role.isNullOrEmpty()) return UserRoles.NO_AUTH
        return when (role[0]) {
            CUSTOMER -> UserRoles.CUSTOMER
            MANAGER -> UserRoles.MANAGER
            TECHNICIAN -> UserRoles.TECHNICIAN
            else -> UserRoles.NO_AUTH
        }
    }
}



