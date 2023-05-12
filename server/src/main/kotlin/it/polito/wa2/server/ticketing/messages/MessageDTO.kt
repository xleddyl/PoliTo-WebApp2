package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.ticketing.tickets.Ticket
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import it.polito.wa2.server.ticketing.tickets.toDTO
import java.sql.Timestamp

data class MessageDTO(
        val id: Long?,
        val ticket: Long,
        val fromCustomer: Boolean,
        val timestamp: Timestamp,
        val attachment: ByteArray?,
        val content: String
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(id, ticket.id!!, fromCustomer, timestamp, attachment, content)
}

