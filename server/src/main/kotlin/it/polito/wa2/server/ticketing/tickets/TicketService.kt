package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.security.access.prepost.PreAuthorize

interface TicketService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun getAll(): List<Ticket>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun getById(ticketId: Long): Ticket

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun createTicket(ticketDTO: TicketDTO): Ticket

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun editTicket(ticketId: Long, ticketDTO: TicketDTO): Ticket

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun deleteTicket(ticketId: Long): Ticket

    @Throws(NotFoundException::class, NotValidException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun updateStatus(ticketId: Long, state: States): Ticket
}