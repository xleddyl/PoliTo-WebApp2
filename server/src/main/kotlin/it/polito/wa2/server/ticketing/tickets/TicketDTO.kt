package it.polito.wa2.server.ticketing.tickets

data class TicketDTO(
    val id: Long,
    val technician: String?,
    val statuses: MutableList<States>,
    val description: String,
    val priority: Int,
) {
    override fun toString(): String {
        return "Ticket(id=$id, technician='${technician}', statuses=$statuses, description='$description', priority=$priority)"
    }
}