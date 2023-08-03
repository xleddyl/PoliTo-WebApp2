package it.polito.wa2.server.products

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, String> {
    @Query("select product from Product product where product.ean IN :ids")
    fun getAllByListOfId(ids: List<String>): List<Product>
}