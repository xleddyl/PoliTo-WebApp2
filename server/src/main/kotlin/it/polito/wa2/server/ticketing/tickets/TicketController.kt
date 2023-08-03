package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<TicketDTO> {
        return ticketService.getAll().map { it.toDTO() }
    }

    @GetMapping("/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ticketId: Long, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        return ticketService.getById(ticketId).toDTO()
    }

    @PostMapping("/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTicket(@Valid @RequestBody ticketDTO: TicketDTO, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        return ticketService.createTicket(ticketDTO).toDTO()
    }

    @PostMapping("/tickets/{ticketId}/{stateString}")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateStatus(@PathVariable ticketId: Long, @PathVariable stateString: String, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        try {
            val state = States.valueOf(stateString.uppercase())
            return ticketService.updateStatus(ticketId, state).toDTO()
        } catch (e: IllegalArgumentException) {
            throw NotValidException("Invalid status")
        }
    }

    @PutMapping("/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun editTicket(@PathVariable ticketId: Long, @Valid @RequestBody ticketDTO: TicketDTO, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        if (ticketId != ticketDTO.id) throw NotValidException("Ticket id and path id don't match")
        return ticketService.editTicket(ticketId, ticketDTO).toDTO()
    }

    @DeleteMapping("/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTicket(@PathVariable ticketId: Long, @AuthenticationPrincipal user: DefaultOAuth2User?): TicketDTO {
        return ticketService.deleteTicket(ticketId).toDTO()
    }
}