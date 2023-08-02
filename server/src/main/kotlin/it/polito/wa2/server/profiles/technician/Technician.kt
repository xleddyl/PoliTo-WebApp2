package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "profiles_technician")
class Technician(
    email: String, name: String, phone: String,
    var specialization: String
) : Profile(email, name, phone)

fun Technician.toDTO(): TechnicianDTO {
    return TechnicianDTO(email, name, phone, specialization)
}

fun TechnicianDTO.fromDTO(): Technician {
    return Technician(email, name, phone, specialization)
}