package it.polito.wa2.server.purchase

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository: JpaRepository<Purchase, Long> {
    fun findByCustomerEmail(email: String): List<Purchase>

    @Query("select purchase from Purchase purchase where purchase.id IN :ids")
    fun getAllByListOfId(ids: List<Long>): List<Purchase>
}