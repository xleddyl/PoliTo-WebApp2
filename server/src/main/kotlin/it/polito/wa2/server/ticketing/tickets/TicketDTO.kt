package it.polito.wa2.server.ticketing.tickets

data class TicketDTO(
    val id: Long?,
    val technician: String?,
    val statuses: MutableList<Statuses>?,
    val description: String,
    val priority: Int?,
    val messagesIDs: MutableSet<Long?>?,
    val purchaseID: Long
)