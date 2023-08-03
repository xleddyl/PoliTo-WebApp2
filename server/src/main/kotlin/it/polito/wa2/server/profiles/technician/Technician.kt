package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.profiles.manager.Manager
import it.polito.wa2.server.ticketing.tickets.Ticket
import jakarta.persistence.*

@Entity
@Table(name = "technicians")
class Technician(
    email: String, name: String, phone: String,
    var specialization: String,
    @OneToMany(mappedBy = "technician", cascade = [CascadeType.ALL])
    var tickets: MutableSet<Ticket> = mutableSetOf(),
    @ManyToOne(cascade = [CascadeType.ALL])
    var manager: Manager
) : Profile(email, name, phone)

fun Technician.toDTO(): TechnicianDTO {
    return TechnicianDTO(email, name, phone, specialization, tickets.map { it.id!! }, manager.email)
}