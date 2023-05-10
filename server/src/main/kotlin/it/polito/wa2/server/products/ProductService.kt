package it.polito.wa2.server.products

interface ProductService {
    fun getAll(): List<ProductDTO>

    fun getById(ean: String): ProductDTO?

    fun addProduct(productDTO: ProductDTO)
}