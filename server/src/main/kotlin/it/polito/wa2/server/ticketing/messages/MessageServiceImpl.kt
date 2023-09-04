package it.polito.wa2.server.ticketing.messages

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketRepository: TicketRepository,
    private val customerRepository: CustomerRepository,
    private val technicianRepository: TechnicianRepository,
) : MessageService {
    override fun getAllForTicket(ticketId: Long, userDetail: UserDetail): List<MessageDTO> {
        // TODO("solo il customer o il technician relativi al ticket o tutti i manager")  ???
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login

        // customer-technician vede solo i propri tickets
        if ((userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchase_Customer_Email(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty())
            ) throw UnauthorizedException("Unauthorized")

        return messageRepository.findMessagesByTicketId(ticketId).map { it.toDTO() }
    }

    override fun getById(ticketId: Long, messageId: Long, userDetail: UserDetail): MessageDTO {
        // TODO("solo il customer o il technician relativi al ticket o tutti i manager")  ???
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login

        // customer-technician vede solo i propri tickets
        if ((userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchase_Customer_Email(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty())
        ) throw UnauthorizedException("Unauthorized")

        return messageRepository.findMessageByIdAndTicketId(messageId, ticketId)?.toDTO()
            ?: throw NotFoundException("Message not found")
    }

    override fun addMessage(messageDTO: MessageDTO, ticketId: Long, userDetail: UserDetail): MessageDTO {
        // TODO("solo il customer o il technician relativi al ticket o tutti i manager")  ???
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login

        // customer-technician vede solo i propri tickets
        if ((userDetail.role == UserRoles.CUSTOMER && ticketRepository.findByPurchase_Customer_Email(userDetail.email)
                .any { it.id == ticketId }) ||
            (userDetail.role == UserRoles.TECHNICIAN && technicianRepository.findByIdOrNull(userDetail.email)?.tickets?.filter { it.id == ticketId }
                .isNullOrEmpty())
        ) throw UnauthorizedException("Unauthorized")

        if (messageDTO.id != null && messageRepository.findByIdOrNull(messageDTO.id) != null) throw DuplicateException("Message id already exists")
        return messageRepository.save(
            Message(
                ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw NotFoundException("Ticket not found"),
                fromCustomer = messageDTO.fromCustomer,
                timestamp = messageDTO.timestamp,
                attachment = messageDTO.attachment,
                content = messageDTO.content,
                new = messageDTO.new
            )
        ).toDTO()
    }
}