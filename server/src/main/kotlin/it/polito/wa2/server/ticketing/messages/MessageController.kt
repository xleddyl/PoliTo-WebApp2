package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping("API/tickets/{ticketId}/messages")
    fun getAllForTicket(@PathVariable ticketId: Long): List<MessageDTO> {
        return messageService.getAllForTicket(ticketId)
    }

    @GetMapping("API/tickets/{ticketId}/messages/{messageId}")
    fun getMessageByIdForTicket(@PathVariable ticketId: Long, @PathVariable messageId: Long): MessageDTO {
        return messageService.getById(ticketId, messageId) ?: throw NotFoundException("Message not found")
    }

    @PostMapping("API/tickets/{ticketId}/messages")
    fun addMessageForTicket(@RequestBody messageDTO: MessageDTO?, @PathVariable ticketId: Long) {
        if (messageDTO == null) throw NotValidException("Message was malformed")
        if (messageDTO.ticket.id != ticketId) throw NotValidException("Message id and path id doesn't match")
        messageService.addMessage(messageDTO, ticketId)
    }

}