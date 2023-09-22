package it.polito.wa2.server.purchase

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : JpaRepository<Purchase, Long?> {
    fun findByCustomerEmail(email: String): List<Purchase>
}