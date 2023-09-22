package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.profiles.technician.Technician
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "managers")
class Manager(
    email: String,
    name: String,
    phone: String,
    var level: Int,

    @OneToMany(mappedBy = "manager", cascade = [CascadeType.ALL])
    var technicians: MutableSet<Technician> = mutableSetOf()
) : Profile(email, name, phone)

fun Manager.toDTO(): ManagerDTO {
    return ManagerDTO(
        email,
        name,
        phone,
        level,
        technicians.map { it.email }.toMutableSet()
    )
}