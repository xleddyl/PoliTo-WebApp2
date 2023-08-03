package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize

interface TicketService {
    fun getAll(userDetail: UserDetail): List<Ticket>

    fun getById(ticketId: Long, userDetail: UserDetail): Ticket

    fun createTicket(ticketDTO: TicketDTO, userDetail: UserDetail): Ticket

    fun editTicket(ticketId: Long, ticketDTO: TicketDTO, userDetail: UserDetail): Ticket

    fun deleteTicket(ticketId: Long, userDetail: UserDetail): Ticket

    fun updateStatus(ticketId: Long, state: States, userDetail: UserDetail): Ticket
}