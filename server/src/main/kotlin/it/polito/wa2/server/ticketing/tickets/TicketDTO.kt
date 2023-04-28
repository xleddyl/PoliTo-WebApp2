package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.ticketing.messages.Message

data class TicketDTO(
    val id: Long?,
    val product: Product,
    val customer: Profile,
    val technician: Profile,
    val statuses: MutableList<States>,
    val description: String,
    val priority: Int,
    val messages: MutableSet<Message>?
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(id, product, customer, technician, statuses, description, priority, messages)
}