package it.polito.wa2.server.ticketing.tickets

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.products.ProductService
import it.polito.wa2.server.products.fromDTO
import it.polito.wa2.server.profiles.customer.CustomerService
import it.polito.wa2.server.profiles.customer.fromDTO
import it.polito.wa2.server.profiles.technician.TechnicianService
import it.polito.wa2.server.profiles.technician.fromDTO
import it.polito.wa2.server.ticketing.messages.Message
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val customerService: CustomerService,
    private val technicianService: TechnicianService,
    private val productService: ProductService
) : TicketService {
    override fun getAll(): List<Ticket> {
        return ticketRepository.findAll()
    }

    override fun getById(ticketId: Long): Ticket {
        val t = ticketRepository.findByIdOrNull(ticketId)
        return t ?: throw NotFoundException("Ticket not found")
    }

    override fun createTicket(ticketDTO: TicketDTO): Ticket {
        if (ticketDTO.id != null && ticketRepository.findByIdOrNull(ticketDTO.id) != null) throw DuplicateException("Ticket id already exists")
        if (ticketDTO.statuses.size != 1 && ticketDTO.statuses.first() != States.OPEN) throw NotValidException("Ticket status is invalid")
        val customer =
            customerService.getByEmail(ticketDTO.customer.email) ?: throw NotValidException("User does not exists")
        val technician = ticketDTO.technician?.email?.let { technicianService.getByEmail(it) }
        val product = productService.getById(ticketDTO.product.ean)

        val messages = mutableSetOf<Message>()

        return ticketRepository.save(
            Ticket(
                product = product.fromDTO(),
                customer = customer.fromDTO(),
                technician = technician?.fromDTO(),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = messages
            )
        )
    }

    override fun editTicket(ticketId: Long, ticketDTO: TicketDTO): Ticket {
        val ticket = getById(ticketId)
        val customer =
            customerService.getByEmail(ticketDTO.customer.email) ?: throw NotValidException("User does not exists")
        val technician = ticketDTO.technician?.email?.let { technicianService.getByEmail(it) }
        val product = productService.getById(ticketDTO.product.ean)
        return ticketRepository.save(
            Ticket(
                id = ticket.id,
                product = product.fromDTO(),
                customer = customer.fromDTO(),
                technician = technician?.fromDTO(),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = ticket.messages
            )
        )
    }

    override fun deleteTicket(ticketId: Long): Ticket {
        val ticket = getById(ticketId)
        ticketRepository.deleteById(ticketId)
        return ticket
    }

    override fun updateStatus(ticketId: Long, state: States): Ticket {
        // OPEN -> RESOLVED -> REOPENED -> IN PROGRESS -> OPEN
        val ticket = getById(ticketId)
        when (ticket.statuses.last()) {
            States.OPEN -> {
                if (state != States.RESOLVED && state != States.CLOSED && state != States.IN_PROGRESS) throw NotValidException(
                    "Invalid status"
                )
            }

            States.CLOSED -> {
                if (state != States.RESOLVED) throw NotValidException("Invalid status")
            }

            States.IN_PROGRESS -> {
                if (state != States.OPEN && state != States.CLOSED && state != States.RESOLVED) throw NotValidException(
                    "Invalid status"
                )
            }

            States.REOPEN -> {
                if (state != States.IN_PROGRESS && state != States.RESOLVED) throw NotValidException("Invalid status")
            }

            States.RESOLVED -> {
                if (state != States.REOPEN && state != States.CLOSED) throw NotValidException("Invalid status")
            }
        }
        ticket.statuses.add(state)
        return ticketRepository.save(
            ticket
        )
    }

}