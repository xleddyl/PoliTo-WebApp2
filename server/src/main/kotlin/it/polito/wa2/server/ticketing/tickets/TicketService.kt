package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException

interface TicketService {
    fun getAll(): List<Ticket>

    @Throws(NotFoundException::class)
    fun getById(ticketId: Long): Ticket

    @Throws(DuplicateException::class)
    fun createTicket(ticketDTO: TicketDTO): Ticket

    @Throws(NotFoundException::class)
    fun editTicket(ticketId: Long, ticketDTO: TicketDTO): Ticket

    @Throws(NotFoundException::class)
    fun deleteTicket(ticketId: Long): Ticket

    @Throws(NotFoundException::class, NotValidException::class)
    fun updateStatus(ticketId: Long, state: States): Ticket
}