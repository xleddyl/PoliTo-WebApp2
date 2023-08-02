package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "customer_profiles")
class CustomerProfile(
    email: String, name: String, phone: String,
    var address: String,

): Profile(email, name, phone) {
}