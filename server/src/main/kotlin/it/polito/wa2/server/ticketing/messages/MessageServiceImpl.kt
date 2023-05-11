package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.ticketing.tickets.TicketDTO
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketRepository: TicketRepository
) : MessageService {
    override fun getAllForTicket(ticketId: Long): List<MessageDTO> {
        val ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw NotFoundException("Ticket not found")
        return messageRepository.findMessagesByTicket(ticket).map { it.toDTO() }
    }

    override fun getById(ticketId: Long, messageId: Long): MessageDTO? {
        val ticket = ticketRepository.findByIdOrNull(ticketId) ?: throw NotFoundException("Ticket not found")
        return messageRepository.findMessageByIdAndTicket(messageId, ticket)?.toDTO()
    }

    override fun addMessage(messageDTO: MessageDTO, ticketId: Long) {
        val ticket = ticketRepository.findByIdOrNull(messageDTO.id) ?: throw NotFoundException("Ticket not found")
        messageRepository.save(
            Message(
                messageDTO.id,
                ticket,
                messageDTO.fromCustomer,
                messageDTO.timestamp,
                messageDTO.attachment,
                messageDTO.content
            )
        )
    }
}