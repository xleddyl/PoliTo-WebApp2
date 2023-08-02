package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping("/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getAllForTicket(@PathVariable ticketId: Long): List<MessageDTO> {
        return messageService.getAllForTicket(ticketId).map { it.toDTO() }
    }

    @GetMapping("/tickets/{ticketId}/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByIdForTicket(@PathVariable ticketId: Long, @PathVariable messageId: Long): MessageDTO {
        return messageService.getById(ticketId, messageId).toDTO()
    }

    @PostMapping("/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMessageForTicket(@Valid @RequestBody messageDTO: MessageDTO, @PathVariable ticketId: Long): MessageDTO {
        if (messageDTO.ticket != ticketId) throw NotValidException("Message id and path id don't match")
        return messageService.addMessage(messageDTO, ticketId).toDTO()
    }

}