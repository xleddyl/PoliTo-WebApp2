package it.polito.wa2.server.products

import it.polito.wa2.server.profiles.customer.Customer
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id var ean: String,
    var sku: String,
    var name: String,
    var brand: String,
    var category: String,
    var price: Float,
    @ManyToMany(mappedBy = "products")
    var customers: MutableSet<Customer> = mutableSetOf()
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, sku, name, brand, category, price)
}