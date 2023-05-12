package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
class TicketController(
        private val ticketService: TicketService
) {

    @GetMapping("API/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<TicketDTO> {
        return ticketService.getAll().map { it.toDTO() }
    }

    @GetMapping("API/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ticketId: Long): TicketDTO {
        return ticketService.getById(ticketId).toDTO()
    }

    @PostMapping("API/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTicket(@RequestBody ticketDTO: TicketDTO?): TicketDTO {
        if (ticketDTO == null) throw NotValidException("Ticket was malformed")
        return ticketService.createTicket(ticketDTO).toDTO()
    }

    @PostMapping("/API/tickets/{ticketId}/{stateString}")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateStatus(@PathVariable ticketId: Long, @PathVariable stateString: String): TicketDTO {
        try {
            val state = States.valueOf(stateString.uppercase())
            return ticketService.updateStatus(ticketId, state).toDTO()
        } catch (e: IllegalArgumentException) {
            throw NotValidException("Invalid status")
        }
    }

    @PutMapping("API/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun editTicket(@PathVariable ticketId: Long, @RequestBody ticketDTO: TicketDTO?): TicketDTO {
        if (ticketDTO == null) throw NotValidException("Ticket was malformed")
        if (ticketId != ticketDTO.id) throw NotValidException("Ticket id and path id doesn't match")
        return ticketService.editTicket(ticketId, ticketDTO).toDTO()
    }

    @DeleteMapping("API/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTicket(@PathVariable ticketId: Long): TicketDTO {
        return ticketService.deleteTicket(ticketId).toDTO()
    }
}