package it.polito.wa2.server.ticketing.messages

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.ticketing.tickets.TicketService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketService: TicketService
) : MessageService {
    override fun getAllForTicket(ticketId: Long): List<Message> {
        val ticket = ticketService.getById(ticketId)
        return messageRepository.findMessagesByTicket(ticket)
    }

    override fun getById(ticketId: Long, messageId: Long): Message {
        val ticket = ticketService.getById(ticketId)

        return messageRepository.findMessageByIdAndTicket(messageId, ticket)

            ?: throw NotFoundException("Message not found")
    }

    override fun addMessage(messageDTO: MessageDTO, ticketId: Long): Message {
        if (messageDTO.id != null && messageRepository.findByIdOrNull(messageDTO.id) != null) throw DuplicateException("Message id already exists")
        val ticket = ticketService.getById(ticketId)
        return messageRepository.save(
            Message(
                //id = messageDTO.id,
                ticket = ticket,
                fromCustomer = messageDTO.fromCustomer,
                timestamp = messageDTO.timestamp,
                attachment = messageDTO.attachment,
                content = messageDTO.content,
                new = messageDTO.new
            )
        )
    }
}