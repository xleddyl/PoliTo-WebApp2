package it.polito.wa2.server.products

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id

@Entity
@Table(name = "products")
class Product (
    @Id var ean: String,
    var sku: String,
    var name: String,
    var brand: String,
    var category: String,
    var price: Float
)