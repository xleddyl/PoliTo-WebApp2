package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class TicketServiceImpl(
    private val ticketRepository: TicketRepository
) : TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ticketId: Long): TicketDTO? {
        return ticketRepository.findByIdOrNull(ticketId)?.toDTO()
    }

    override fun createTicket(ticket: TicketDTO) {
        if (ticketRepository.findByIdOrNull(ticket.id) != null) throw DuplicateException("Ticket id already exists")
        ticketRepository.save(
            Ticket(
                id = ticket.id,
                product = ticket.product,
                customer = ticket.customer,
                technician = ticket.technician,
                statuses = ticket.statuses,
                description = ticket.description,
                priority = ticket.priority,
                messages = ticket.messages
            )
        )
    }

    override fun editTicket(ticketId: Long, ticket: TicketDTO) {
        if (ticketRepository.findByIdOrNull(ticket.id) == null) throw NotFoundException("Ticket not found")
        ticketRepository.save(
            Ticket(
                id = ticket.id,
                product = ticket.product,
                customer = ticket.customer,
                technician = ticket.technician,
                statuses = ticket.statuses,
                description = ticket.description,
                priority = ticket.priority,
                messages = ticket.messages
            )
        )
    }
}