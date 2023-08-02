package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "profiles_Manager")
class Manager(
    email: String, name: String, phone: String,
    var level: Int
) : Profile(email, name, phone)

fun Manager.toDTO(): ManagerDTO {
    return ManagerDTO(email, name, phone, level)
}

fun ManagerDTO.fromDTO(): Manager {
    return Manager(email, name, phone, level)
}