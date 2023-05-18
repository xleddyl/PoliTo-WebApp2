package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.ticketing.tickets.TicketService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketService: TicketService
) : MessageService {
    override fun getAllForTicket(ticketId: Long): List<MessageDTO> {
        val ticket = ticketService.getById(ticketId)
        return messageRepository.findMessagesByTicket(ticket.fromDTO()).map { it.toDTO() }
    }

    override fun getById(ticketId: Long, messageId: Long): MessageDTO {
        val ticket = ticketService.getById(ticketId)
        return messageRepository.findMessageByIdAndTicket(messageId, ticket.fromDTO())?.toDTO()
            ?: throw NotFoundException("Message not found")
    }

    override fun addMessage(messageDTO: MessageDTO, ticketId: Long): MessageDTO {
        if (messageDTO.id != null && messageRepository.findByIdOrNull(messageDTO.id) != null) throw DuplicateException("Message id already exists")
        val ticket = ticketService.getById(ticketId)
        return messageRepository.save(
            Message(
                messageDTO.id,
                ticket.fromDTO(),
                messageDTO.fromCustomer,
                messageDTO.timestamp,
                messageDTO.attachment,
                messageDTO.content
            )
        ).toDTO()
    }
}