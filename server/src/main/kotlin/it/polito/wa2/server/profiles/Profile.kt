package it.polito.wa2.server.profiles

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
open class Profile(@Id var email: String, var name: String, var phone: String)