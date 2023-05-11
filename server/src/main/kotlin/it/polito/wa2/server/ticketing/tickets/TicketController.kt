package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class TicketController(
        private val ticketService: TicketService
) {

    @GetMapping("API/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<TicketDTO> {
        return ticketService.getAll()
    }

    @GetMapping("API/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ticketId: Long): TicketDTO {
        val ticketDTO = ticketService.getById(ticketId)
        if (ticketDTO != null) {
            return ticketDTO
        }
        throw NotFoundException("Ticket not found")
    }

    @PostMapping("API/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTicket(@RequestBody ticketDTO: TicketDTO?): TicketDTO {
        if (ticketDTO == null) throw NotValidException("Ticket was malformed")
        return ticketService.createTicket(ticketDTO)
    }

    @PutMapping("API/tickets/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun editTicket(@PathVariable ticketId: Long, @RequestBody ticketDTO: TicketDTO?): TicketDTO {
        if (ticketDTO == null) throw NotValidException("Ticket was malformed")
        if (ticketId != ticketDTO.id) throw NotValidException("Ticket id and path id doesn't match")
        return ticketService.editTicket(ticketId, ticketDTO)
    }
}