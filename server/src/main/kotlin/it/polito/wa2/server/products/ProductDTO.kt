package it.polito.wa2.server.products

data class ProductDTO(
        val ean: String,
        val sku: String,
        val name: String,
        val brand: String,
        val category: String,
        val price: Float
) {
    fun fromDTO(): Product {
        return Product(ean, sku, name, brand, category, price)
    }

}

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, sku, name, brand, category, price)
}
