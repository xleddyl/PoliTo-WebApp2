package it.polito.wa2.server.ticketing.messages

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Observed
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
    fun addMessageForTicket(@RequestBody messageDTO: MessageDTO?, @PathVariable ticketId: Long): MessageDTO {
        if (messageDTO == null) throw NotValidException("Message was malformed")
        if (messageDTO.ticket != ticketId) throw NotValidException("Message id and path id doesn't match")
        return messageService.addMessage(messageDTO, ticketId).toDTO()
    }

}