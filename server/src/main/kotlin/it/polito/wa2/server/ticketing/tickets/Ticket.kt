package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.customer.Customer
import it.polito.wa2.server.profiles.technician.Technician
import it.polito.wa2.server.purchase.Purchase
import it.polito.wa2.server.ticketing.messages.Message
import jakarta.persistence.*

enum class States {
    OPEN, CLOSED, IN_PROGRESS, RESOLVED, REOPEN
}

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    var purchase: Purchase? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    var customer: Customer,
    @ManyToOne(cascade = [CascadeType.ALL])
    var technician: Technician?,
    @Enumerated(value = EnumType.STRING)
    var statuses: MutableList<States> = mutableListOf(States.OPEN),
    var description: String,
    var priority: Int,
    @OneToMany(mappedBy = "ticket")
    var messages: MutableSet<Message> = mutableSetOf()
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(
        id,
        purchase?.id!!,
        customer.email,
        technician?.email,
        statuses,
        description,
        priority,
        messages.map { it.id!! }
    )
}