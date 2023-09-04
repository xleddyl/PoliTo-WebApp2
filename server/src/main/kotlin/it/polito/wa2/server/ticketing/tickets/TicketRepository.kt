package it.polito.wa2.server.ticketing.tickets

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<Ticket, Long?> {
    @Query("select ticket from Ticket ticket where ticket.id IN :ids")
    fun getAllByListOfId(ids: List<Long>): List<Ticket>

    @Query("select ticket from Ticket ticket where ticket.purchase.customer.email = :email")
    fun findByPurchase_Customer_Email(email: String): List<Ticket>
}