package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "technician_profiles")
class TechnicianProfile(
    email: String, name: String, phone: String,
    var companyName: String,
    var specialization: String,

): Profile(email, name, phone) {
}