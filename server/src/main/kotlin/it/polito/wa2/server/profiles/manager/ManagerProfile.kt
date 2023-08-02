package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "manager_profiles")
class ManagerProfile(
    email: String, name: String, phone: String,
    // other parameters

): Profile(email, name, phone) {
}