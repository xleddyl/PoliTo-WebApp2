package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.security.aut.UserDetail

interface TicketService {
    fun getAll(userDetail: UserDetail): List<TicketDTO>

    fun getById(ticketId: Long, userDetail: UserDetail): TicketDTO

    fun createTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO

    fun editTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO

    fun deleteTicket(ticketId: Long, userDetail: UserDetail)

    fun updateStatus(ticketId: Long, state: States, userDetail: UserDetail): TicketDTO
}