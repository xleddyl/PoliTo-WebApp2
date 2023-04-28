package it.polito.wa2.server.profiles

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

enum class Roles {
    CUSTOMER, ADMIN, MANAGER, TECHNICIAN
}

@Entity
@Table(name = "profiles")
class Profile(
    @Id var email: String,
    var name: String,
    @Enumerated(value = EnumType.STRING)
    var role: Roles = Roles.CUSTOMER,
    var phone: String
    // array of products
)