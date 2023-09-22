package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.security.aut.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MessageController(
    private val messageService: MessageService,
    private val authService: AuthService
) {
    @GetMapping("/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getAllForTicket(
        @PathVariable ticketId: Long,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): List<MessageDTO> {
        return messageService.getAllForTicket(ticketId, authService.getUserDetails(user))
    }

    @GetMapping("/tickets/{ticketId}/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageByIdForTicket(
        @PathVariable ticketId: Long,
        @PathVariable messageId: Long,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): MessageDTO {
        return messageService.getById(ticketId, messageId, authService.getUserDetails(user))
    }

    @PostMapping("/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMessageForTicket(
        @Valid @RequestBody messageDTO: MessageDTO,
        @PathVariable ticketId: Long,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): MessageDTO {
        if (messageDTO.ticket != ticketId) throw NotValidException("Message id and path id don't match")
        return messageService.addMessage(messageDTO, ticketId, authService.getUserDetails(user))
    }

}