package it.polito.wa2.server.ticketing.tickets

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import it.polito.wa2.server.purchase.PurchaseRepository
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
    private val productRepository: ProductRepository,
    private val purchaseRepository: PurchaseRepository,
) : TicketService {
    override fun getAll(userDetail: UserDetail): List<TicketDTO> {

        return ticketRepository.findAll().map { it.toDTO() }

        /*
        return when (userDetail.role) {
            UserRoles.CUSTOMER -> {
                ticketRepository.findByPurchaseCustomerEmail(userDetail.email).map { it.toDTO() }
                // customer vede i suoi ticket
            }

            UserRoles.TECHNICIAN -> {
                technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.map { it.toDTO() } ?: emptyList()
                // technician vede i suoi ticket
            }

            UserRoles.MANAGER -> {
                ticketRepository.findAll().map { it.toDTO() }
                // manager vede tutto
            }

            else -> {
                throw UnauthorizedException("Unauthorized") // no login
            }
        }

         */
    }

    override fun getById(ticketId: Long, userDetail: UserDetail): TicketDTO {
        val ticketDTO =
            ticketRepository.findByIdOrNull(ticketId)?.toDTO() ?: throw NotFoundException("Ticket not found")

        // customer-technician vede il ticket solo se è suo
        if ((userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchaseCustomerEmail(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && !technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty()) ||
            userDetail.role == UserRoles.MANAGER
        ) {
            return ticketDTO
        } else {
            throw UnauthorizedException("Unauthorized")
        }
    }

    override fun createTicket(ticketDTO: TicketDTO, purchaseId: Long, userDetail: UserDetail): TicketDTO {
        // TODO("solo il customer o il manager")  ???
        if (userDetail.role != UserRoles.CUSTOMER && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")

        if (ticketRepository.findByIdOrNull(ticketDTO.id) != null) throw DuplicateException("Ticket already exists")
        if (ticketDTO.statuses.size != 1 && ticketDTO.statuses.first() != States.OPEN) throw NotValidException("Ticket status is invalid")
        val purchase =
            purchaseRepository.findByIdOrNull(purchaseId) ?: throw NotValidException("Purchase does not exists")

        return ticketRepository.save(
            Ticket(
                purchase = purchase,
                technician = technicianRepository.findByIdOrNull(ticketDTO.technician)
                    ?: throw NotValidException("Technician does not exists"),
                statuses = ticketDTO.statuses,
                description = ticketDTO.description,
                priority = ticketDTO.priority,
            )
        ).toDTO()
    }

    override fun editTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO {
        // TODO("solo il customer o il manager")  ???
        if (userDetail.role != UserRoles.CUSTOMER && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")

        val oldTicket =
            ticketRepository.findByIdOrNull(ticketDTO.id) ?: throw DuplicateException("Ticket does not exists")

        // customer modifica solo i propri ticket
        if (userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchaseCustomerEmail(userDetail.email)
                .any { it.id == ticketDTO.id }
        ) throw UnauthorizedException("Unauthorized")

        val newTicket = Ticket(
            purchase = oldTicket.purchase,
            technician = technicianRepository.findByIdOrNull(ticketDTO.technician)
                ?: throw NotValidException("Technician does not exists"),
            statuses = ticketDTO.statuses,
            description = ticketDTO.description,
            priority = ticketDTO.priority,
        )
        newTicket.messages = oldTicket.messages // messages preserved, to edit messages use the MessageService


        return ticketRepository.save(newTicket).toDTO()
    }

    override fun deleteTicket(ticketId: Long, userDetail: UserDetail) {
        // TODO("solo il customer o il manager")  ???
        if (userDetail.role != UserRoles.CUSTOMER && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")

        // customer elimina solo i propri ticket
        if (userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchaseCustomerEmail(userDetail.email)
                .any { it.id == ticketId }
        ) throw UnauthorizedException("Unauthorized")


        return ticketRepository.deleteById(ticketId)
    }

    override fun updateStatus(ticketId: Long, state: States, userDetail: UserDetail): TicketDTO {
        // OPEN -> RESOLVED -> REOPENED -> IN PROGRESS -> OPEN
        // TODO("solo il technician e il manager")  ??? o tutti ?
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login

        val ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw DuplicateException("Ticket does not exists")

        // customer-technician modifica solo i propri ticket
        if ((userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchaseCustomerEmail(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty())
        ) throw UnauthorizedException("Unauthorized")


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