package it.polito.wa2.server.products

data class ProductDTO(
    val ean: String,
    val sku: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Float
)
