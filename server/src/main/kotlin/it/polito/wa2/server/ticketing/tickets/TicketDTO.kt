package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.products.ProductDTO
import it.polito.wa2.server.products.toDTO
import it.polito.wa2.server.profiles.ProfileDTO
import it.polito.wa2.server.profiles.toDTO
import it.polito.wa2.server.ticketing.messages.Message
import it.polito.wa2.server.ticketing.messages.MessageDTO
import it.polito.wa2.server.ticketing.messages.toDTO

data class TicketDTO(
        val id: Long?,
        val product: ProductDTO,
        val customer: ProfileDTO,
        val technician: ProfileDTO?,
        val statuses: MutableList<States>,
        val description: String,
        val priority: Int,
        val messages: MutableSet<Long>?
)

fun Ticket.toDTO(): TicketDTO {
    val m = mutableSetOf<Long>()
    messages?.forEach { m.add(it.id!!) }
    return TicketDTO(
            id,
            product.toDTO(),
            customer.toDTO(),
            technician?.toDTO(),
            statuses,
            description,
            priority,
            m
    )
}