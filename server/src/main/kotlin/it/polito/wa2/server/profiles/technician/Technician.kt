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

    @ManyToOne
    @JoinColumn(name = "manager_email")
    var manager: Manager? = null

) : Profile(email, name, phone) {

    @OneToMany(mappedBy = "technician")
    var tickets: MutableSet<Ticket> = mutableSetOf()

    fun toDTO(): TechnicianDTO {
        return TechnicianDTO(email, name, phone, specialization)
    }
}
