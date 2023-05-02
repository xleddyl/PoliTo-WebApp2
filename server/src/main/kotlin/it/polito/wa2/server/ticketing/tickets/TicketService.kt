package it.polito.wa2.server.ticketing.tickets

interface TicketService {
    fun getAll(): List<TicketDTO>

    fun getById(ticketId: Long): TicketDTO?

    fun createTicket(ticket: TicketDTO)

    fun editTicket(ticketId: Long, ticket: TicketDTO)
}