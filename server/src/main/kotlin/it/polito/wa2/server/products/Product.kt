package it.polito.wa2.server.products

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id var ean: String,
    var sku: String,
    var name: String,
    var brand: String,
    var category: String,
    var price: Float
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, sku, name, brand, category, price)
}

fun ProductDTO.fromDTO(): Product {
    return Product(ean, sku, name, brand, category, price)
}