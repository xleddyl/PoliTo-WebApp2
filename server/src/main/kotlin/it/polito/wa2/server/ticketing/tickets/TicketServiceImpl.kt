package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.products.ProductService
import it.polito.wa2.server.profiles.ProfileService
import it.polito.wa2.server.ticketing.messages.Message
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val profileService: ProfileService,
    private val productService: ProductService
) : TicketService {
    override fun getAll(): List<TicketDTO> {
        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ticketId: Long): TicketDTO {
        return ticketRepository.findByIdOrNull(ticketId)?.toDTO() ?: throw NotFoundException("Ticket not found")
    }

    override fun createTicket(ticketDTO: TicketDTO): TicketDTO {
        if (ticketDTO.id != null && ticketRepository.findByIdOrNull(ticketDTO.id) != null) throw DuplicateException("Ticket id already exists")
        val customer = profileService.getByEmail(ticketDTO.customer.email)
        val product = productService.getById(ticketDTO.product.ean)
        val messages = mutableSetOf<Message>()
        ticketDTO.messages?.forEach { messages.add(it.fromDTO()) }
        return ticketRepository.save(
            Ticket(
                id = ticketDTO.id,
                product = product.fromDTO(),
                customer = customer.fromDTO(),
                technician = ticketDTO.technician?.fromDTO(),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = messages
            )
        ).toDTO()
    }

    override fun editTicket(ticketId: Long, ticketDTO: TicketDTO): TicketDTO {
        val ticket = getById(ticketId)
        val customer = profileService.getByEmail(ticketDTO.customer.email)
        val product = productService.getById(ticketDTO.product.ean)
        val messages = mutableSetOf<Message>()
        ticketDTO.messages?.forEach { messages.add(it.fromDTO()) }
        return ticketRepository.save(
            Ticket(
                id = ticket.id,
                product = product.fromDTO(),
                customer = customer.fromDTO(),
                technician = ticketDTO.technician?.fromDTO(),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = messages
            )
        ).toDTO()
    }

    override fun updateStatus(ticketId: Long, state: States): TicketDTO {
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
            ticket.fromDTO()
        ).toDTO()
    }

}