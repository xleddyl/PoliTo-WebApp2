package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException

interface TicketService {
    fun getAll(): List<TicketDTO>

    @Throws(NotFoundException::class)
    fun getById(ticketId: Long): TicketDTO

    @Throws(DuplicateException::class)
    fun createTicket(ticketDTO: TicketDTO): TicketDTO

    @Throws(NotFoundException::class)
    fun editTicket(ticketId: Long, ticketDTO: TicketDTO): TicketDTO
}