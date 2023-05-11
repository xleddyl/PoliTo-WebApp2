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
        val messages: MutableSet<MessageDTO>?
) {
    fun fromDTO(): Ticket {
        val m = mutableSetOf<Message>()
        messages?.forEach { m.add(it.fromDTO()) }
        return Ticket(
                id,
                product.fromDTO(),
                customer.fromDTO(),
                technician?.fromDTO(),
                statuses,
                description,
                priority,
                m
        )
    }
}

fun Ticket.toDTO(): TicketDTO {
    val m = mutableSetOf<MessageDTO>()
    messages?.forEach { m.add(it.toDTO()) }
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