package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.profiles.technician.Technician
import it.polito.wa2.server.purchase.Purchase
import it.polito.wa2.server.ticketing.messages.Message
import jakarta.persistence.*

enum class Statuses {
    OPEN, CLOSED, IN_PROGRESS, RESOLVED, REOPEN
}

data class TicketStatus(val status: String)

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(mappedBy = "ticket", fetch = FetchType.EAGER)
    var purchase: Purchase,

    @ManyToOne
    var technician: Technician? = null,

    @Enumerated(value = EnumType.STRING)
    var statuses: MutableList<Statuses> = mutableListOf(Statuses.OPEN),

    var description: String,

    var priority: Int? = null,

    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.ALL])
    var messages: MutableSet<Message> = mutableSetOf()
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(
        id!!,
        technician?.email,
        statuses,
        description,
        priority,
        messages.map { it.id }.toMutableSet(), purchase.id!!
    )
}