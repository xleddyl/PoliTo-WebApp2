package it.polito.wa2.server.ticketing.messages

import java.sql.Timestamp

data class MessageDTO(
    val id: Long?,
    val ticket: Long,
    val fromCustomer: Boolean,
    val timestamp: Timestamp?,
    val attachment: String?, //Base64
    val content: String?
)
