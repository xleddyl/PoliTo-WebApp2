package it.polito.wa2.server.products

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException

interface ProductService {
    fun getAll(): List<ProductDTO>

    @Throws(NotFoundException::class)
    fun getById(ean: String): ProductDTO

    @Throws(NotFoundException::class)
    fun getByIdP(ean: String): Product

    @Throws(DuplicateException::class)
    fun addProduct(productDTO: ProductDTO): ProductDTO
}