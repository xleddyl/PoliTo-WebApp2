package it.polito.wa2.server.ticketing.tickets

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<Ticket, Long?> {
    @Query("select ticket from Ticket ticket where ticket.purchase.customer.email = :email")
    fun findByPurchaseCustomerEmail(email: String): List<Ticket>
}