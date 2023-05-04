package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.web.bind.annotation.*

@RestController
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("API/tickets")
    fun getAll(): List<TicketDTO> {
        return ticketService.getAll()
    }

    @GetMapping("API/tickets/{ticketId}")
    fun getById(@PathVariable ticketId: Long): TicketDTO {
        val ticket = ticketService.getById(ticketId)
        if (ticket != null) {
            return ticket
        }
        throw NotFoundException("Ticket not found")
    }

    @PostMapping("API/tickets")
    fun createTicket(@RequestBody ticket: TicketDTO?) {
        if (ticket == null) throw NotValidException("Ticket was malformed")
        ticketService.createTicket(ticket)
    }

    @PutMapping("API/tickets/{ticketId}")
    fun editTicket(@PathVariable ticketId: Long, @RequestBody ticket: TicketDTO?) {
        if (ticket == null) throw NotValidException("Ticket was malformed")
        if (ticketId != ticket.id) throw NotValidException("Ticket id and path id doesn't match")
        ticketService.editTicket(ticketId, ticket)
    }
}