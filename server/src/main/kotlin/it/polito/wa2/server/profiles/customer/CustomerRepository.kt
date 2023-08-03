package it.polito.wa2.server.profiles.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, String> {
    @Query("select customer from Customer customer where customer.email IN :ids")
    fun getAllByListOfId(ids: List<String>): List<Customer>
}