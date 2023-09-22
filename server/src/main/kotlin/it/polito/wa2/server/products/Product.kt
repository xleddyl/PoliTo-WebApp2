package it.polito.wa2.server.products

import it.polito.wa2.server.purchase.Purchase
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id
    var ean: String,

    var sku: String,
    var name: String,
    var brand: String,
    var category: String,
    var price: Float,

    @OneToMany(mappedBy = "product")
    var purchases: MutableSet<Purchase> = mutableSetOf()
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(
        ean,
        sku,
        name,
        brand,
        category,
        price
    )
}