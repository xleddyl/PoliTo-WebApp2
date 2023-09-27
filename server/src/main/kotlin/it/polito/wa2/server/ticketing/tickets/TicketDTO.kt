package it.polito.wa2.server.ticketing.tickets

import java.sql.Timestamp

data class TicketDTO(
    val id: Long?,
    val technicianID: String?,
    val statuses: MutableList<Statuses>?,
    val description: String,
    val priority: Int?,
    val messagesIDs: MutableSet<Long?>?,
    val purchaseID: Long,
    val date: Timestamp?
)