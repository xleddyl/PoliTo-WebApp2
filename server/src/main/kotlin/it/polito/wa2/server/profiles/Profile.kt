package it.polito.wa2.server.profiles

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "profiles")
class Profile (
    @Id var email: String,
    var name: String,
    var role: String,
    var phone: String
)