package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.security.aut.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class TicketController(
    private val ticketService: TicketService,
    private val authService: AuthService
) {

    @GetMapping("/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<TicketDTO> {
        return ticketService.getAll(authService.getUserDetails(user))
    }

    @GetMapping("/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ticketId: Long, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        return ticketService.getById(ticketId, authService.getUserDetails(user))
    }

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTicket(
        @Valid @RequestBody ticketDTO: TicketDTO,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): TicketDTO {
        return ticketService.createTicket(ticketDTO, authService.getUserDetails(user))
    }

    @PutMapping("/tickets/{ticketId}/status")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateStatus(
        @PathVariable ticketId: Long,
        @Valid @RequestBody ticketStatus: TicketStatus,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): TicketDTO {
        println("\n\n\nHERE UPDATE STATUS")
        try {
            val status = Statuses.valueOf(ticketStatus.status.uppercase())
            return ticketService.updateStatus(ticketId, status, authService.getUserDetails(user))
        } catch (e: IllegalArgumentException) {
            throw NotValidException("Invalid status")
        }
    }

    @PutMapping("/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun editTicket(
        @PathVariable ticketId: Long,
        @Valid @RequestBody ticketDTO: TicketDTO,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): TicketDTO {
        if (ticketId != ticketDTO.id) throw NotValidException("Ticket id and path id don't match")
        return ticketService.editTicket(ticketDTO, authService.getUserDetails(user))
    }
}