package it.polito.wa2.server.ticketing.tickets

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController (
    private val ticketService: TicketService
) {

    @GetMapping("API/tickets")
    fun getAll(): List<TicketDTO> {
        return ticketService.getAll()
    }

    @GetMapping("API/tickets/{ticketId}")
    fun getAllById(@PathVariable ticketId: Long): TicketDTO {
        val ticket = ticketService.getById(ticketId)
        if (ticket != null) {
            return ticket
        }
        TODO("THROW -> Product not found")
    }

    @PostMapping("API/tickets")
    fun createTicket(@RequestBody ticket: TicketDTO?) {
        if (ticket == null) {
            TODO("THROW -> Invalid ticket")
        }
        ticketService.createTicket(ticket)
    }

    @PutMapping("API/tickets/{ticketId}")
    fun editTicket(@PathVariable ticketId: Long, @RequestBody ticket: TicketDTO?) {
        if (ticket == null) {
            TODO("THROW -> Invalid ticket")
        }
        if (ticketId != ticket.id) {
            TODO("THROW -> Invalid id")
        }
        ticketService.editTicket(ticketId, ticket)
    }
}