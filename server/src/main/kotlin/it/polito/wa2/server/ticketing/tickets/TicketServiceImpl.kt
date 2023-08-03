package it.polito.wa2.server.ticketing.tickets

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import it.polito.wa2.server.security.aut.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val customerRepository: CustomerRepository,
    private val technicianRepository: TechnicianRepository,
    private val productRepository: ProductRepository
) : TicketService {
    override fun getAll(userDetail: UserDetail): List<TicketDTO> {
        // TODO("solo il customer o il technician relativi al ticket o tutti i manager")  ???

        return ticketRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ticketId: Long, userDetail: UserDetail): TicketDTO {
        // TODO("solo il customer o il technician relativi al ticket o tutti i manager")  ???

        return ticketRepository.findByIdOrNull(ticketId)?.toDTO() ?: throw NotFoundException("Ticket not found")
    }

    override fun createTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO {
        // TODO("solo il customer o il manager")  ???

        if (ticketRepository.findByIdOrNull(ticketDTO.id) != null) throw DuplicateException("Ticket already exists")
        if (ticketDTO.statuses.size != 1 && ticketDTO.statuses.first() != States.OPEN) throw NotValidException("Ticket status is invalid")
        return ticketRepository.save(
            Ticket(
                product = productRepository.findByIdOrNull(ticketDTO.product)
                    ?: throw NotValidException("Product does not exists"),
                customer = customerRepository.findByIdOrNull(ticketDTO.customer)
                    ?: throw NotValidException("Customer does not exists"),
                technician = technicianRepository.findByIdOrNull(ticketDTO.technician)
                    ?: throw NotValidException("Technician does not exists"),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = mutableSetOf() // starting with empty list of messages
            )
        ).toDTO()
    }

    override fun editTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO {
        // TODO("solo il customer o il manager")  ???

        val ticket = ticketRepository.findByIdOrNull(ticketDTO.id) ?: throw DuplicateException("Ticket does not exists")
        return ticketRepository.save(
            Ticket(
                product = productRepository.findByIdOrNull(ticketDTO.product)
                    ?: throw NotValidException("Product does not exists"),
                customer = customerRepository.findByIdOrNull(ticketDTO.customer)
                    ?: throw NotValidException("Customer does not exists"),
                technician = technicianRepository.findByIdOrNull(ticketDTO.technician)
                    ?: throw NotValidException("Technician does not exists"),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = ticket.messages // messages preserved, to edit messages use the MessageService
            )
        ).toDTO()
    }

    override fun deleteTicket(ticketId: Long, userDetail: UserDetail) {
        // TODO("solo il customer o il manager")  ???
        return ticketRepository.deleteById(ticketId)
    }

    override fun updateStatus(ticketId: Long, state: States, userDetail: UserDetail): TicketDTO {
        // OPEN -> RESOLVED -> REOPENED -> IN PROGRESS -> OPEN
        // TODO("solo il technician e il manager")  ???

        val ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw DuplicateException("Ticket does not exists")
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
        return ticketRepository.save(ticket).toDTO()
    }
}