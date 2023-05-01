package it.polito.wa2.server.ticketing.messages

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping("API/tickets/{ticketId}/messages")
    fun getAllForTicket(@PathVariable ticketId: Long): List<MessageDTO> {
        return messageService.getAllForTicket(ticketId)
        /* TODO("check errors / if empty") */
    }

    @GetMapping("API/tickets/{ticketId}/messages/{messageId}")
    fun getMessageByIdForTicket(@PathVariable ticketId: Long, @PathVariable messageId: Long): MessageDTO {
        return messageService.getById(ticketId, messageId) ?: TODO("check errors / if empty")
    }

    @PostMapping("API/tickets/{ticketId}/messages")
    fun addMessageForTicket(@RequestBody messageDTO: MessageDTO?, @PathVariable ticketId: Long) {
        if (messageDTO == null) {
            TODO("THROW exception")
        }
        if (messageDTO.ticket.id != ticketId) {
            TODO("THROW exception")
        }
        messageService.addMessage(messageDTO, ticketId)
    }

}