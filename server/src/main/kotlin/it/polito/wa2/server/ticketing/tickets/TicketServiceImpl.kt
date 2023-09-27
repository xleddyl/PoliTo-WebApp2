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
import java.sql.Date
import java.sql.Timestamp

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
        return when (userDetail.role) {
            UserRoles.CUSTOMER -> {
                ticketRepository.findByPurchaseCustomerEmail(userDetail.email).map { it.toDTO() }
            }

            UserRoles.TECHNICIAN -> {
                technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.map { it.toDTO() } ?: emptyList()
            }

            UserRoles.MANAGER -> {
                ticketRepository.findAll().map { it.toDTO() }
            }

            else -> {
                throw UnauthorizedException("Unauthorized") // no login
            }
        }

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

    override fun createTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO {
        if (userDetail.role != UserRoles.CUSTOMER && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")

        val purchase = purchaseRepository.findByIdOrNull(ticketDTO.purchaseID)
            ?: throw NotValidException("Purchase does not exists")
        if (userDetail.role == UserRoles.CUSTOMER && purchase.customer.email != userDetail.email) throw UnauthorizedException(
            "Unauthorized"
        ) // customer non può il ticket per qualcun altro

        // val technician = technicianRepository.findByIdOrNull(ticketDTO.technician) ?: throw NotValidException("Technician does not exists")

        val ticket = ticketRepository.save(
            Ticket(
                purchase = purchase,
                statuses = mutableListOf(Statuses.OPEN),
                description = ticketDTO.description,
                date = Timestamp(System.currentTimeMillis())
            )
        )
        purchase.ticket = ticket
        purchaseRepository.save(purchase)

        return ticket.toDTO()
    }

    override fun editTicket(ticketDTO: TicketDTO, userDetail: UserDetail): TicketDTO {
        if (userDetail.role != UserRoles.CUSTOMER && userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")

        val oldTicket = ticketRepository.findByIdOrNull(ticketDTO.id) ?: throw NotFoundException("Ticket not found")

        val purchase = purchaseRepository.findByIdOrNull(ticketDTO.purchaseID)
            ?: throw NotValidException("Purchase does not exists")
        if (userDetail.role == UserRoles.CUSTOMER && purchase.customer.email != userDetail.email) throw UnauthorizedException(
            "Unauthorized"
        ) // customer non può il ticket per qualcun altro

        val technician = technicianRepository.findByIdOrNull(ticketDTO.technicianID)
            ?: throw NotValidException("Technician does not exists")

        val ticket = ticketRepository.save(
            Ticket(
                purchase = purchase,
                technician = technician,
                statuses = mutableListOf(Statuses.OPEN),
                description = ticketDTO.description,
                priority = ticketDTO.priority,
                messages = oldTicket.messages,
                id = oldTicket.id,
                date = oldTicket.date
            )
        )
        purchase.ticket = ticket
        purchaseRepository.save(purchase)

        return ticket.toDTO()
    }

    override fun updateStatus(ticketId: Long, status: Statuses, userDetail: UserDetail): TicketDTO {
        // OPEN -> RESOLVED -> REOPENED -> IN PROGRESS -> OPEN

        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login

        val ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw DuplicateException("Ticket does not exists")

        // customer-technician modifica solo i propri ticket
        if ((userDetail.role == UserRoles.CUSTOMER && !ticketRepository.findByPurchaseCustomerEmail(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty())
        ) throw UnauthorizedException("Unauthorized")

        println("\n\n\n$ticketId: new $status and old ${ticket.statuses.last()}")

        when (ticket.statuses.last()) {
            Statuses.OPEN -> {
                if (status != Statuses.RESOLVED && status != Statuses.CLOSED && status != Statuses.IN_PROGRESS) throw NotValidException(
                    "Invalid status"
                )
            }

            Statuses.CLOSED -> {
                if (status != Statuses.REOPEN) throw NotValidException("Invalid status")
            }

            Statuses.IN_PROGRESS -> {
                if (status != Statuses.OPEN && status != Statuses.CLOSED && status != Statuses.RESOLVED) throw NotValidException(
                    "Invalid status"
                )
            }

            Statuses.REOPEN -> {
                if (status != Statuses.IN_PROGRESS && status != Statuses.RESOLVED && status != Statuses.CLOSED) throw NotValidException(
                    "Invalid status"
                )
            }

            Statuses.RESOLVED -> {
                if (status != Statuses.REOPEN && status != Statuses.CLOSED) throw NotValidException("Invalid status")
            }
        }
        ticket.statuses.add(status)
        return ticketRepository.save(ticket).toDTO()
    }
}