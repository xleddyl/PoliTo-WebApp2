package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.ticketing.tickets.Ticket
import java.sql.Timestamp

class MessageDTO(
    val id: Long?,
    val ticket: Ticket,
    val fromCustomer: Boolean,
    val timestamp: Timestamp,
    val attachment: ByteArray?,
    val content: String
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(id, ticket, fromCustomer, timestamp, attachment, content)
}