package it.polito.wa2.server.ticketing.tickets

data class TicketDTO(
    val id: Long?,
    val purchase: Long,
    val customer: String,
    val technician: String?,
    val statuses: MutableList<States>,
    val description: String,
    val priority: Int,
    val messages: List<Long>?
)