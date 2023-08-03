package it.polito.wa2.server.profiles

import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.MANAGER
import it.polito.wa2.server.security.TECHNICIAN
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

enum class UserRoles {
    NO_AUTH, CUSTOMER, MANAGER, TECHNICIAN
}

@MappedSuperclass
open class Profile(@Id var email: String, var name: String, var phone: String)