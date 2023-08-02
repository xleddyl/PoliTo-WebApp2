package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.products.ProductDTO
import it.polito.wa2.server.profiles.customer.CustomerDTO
import it.polito.wa2.server.profiles.technician.TechnicianDTO

data class TicketDTO(
    val id: Long?,
    val product: ProductDTO,
    val customer: CustomerDTO,
    val technician: TechnicianDTO?,
    val statuses: MutableList<States>,
    val description: String,
    val priority: Int,
    val messages: MutableSet<Long>?
)