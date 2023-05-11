package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.ticketing.tickets.TicketDTO
import it.polito.wa2.server.ticketing.tickets.toDTO
import java.sql.Timestamp

class MessageDTO(
        val id: Long?,
        val ticket: TicketDTO,
        val fromCustomer: Boolean,
        val timestamp: Timestamp,
        val attachment: ByteArray?,
        val content: String
) {
    fun fromDTO(): Message {
        return Message(id, ticket.fromDTO(), fromCustomer, timestamp, attachment, content)
    }
}

fun Message.toDTO(): MessageDTO {
    return MessageDTO(id, ticket.toDTO(), fromCustomer, timestamp, attachment, content)
}

