package it.polito.wa2.server.products

import it.polito.wa2.server.purchase.PurchaseDTO
import it.polito.wa2.server.security.aut.UserDetail

interface ProductService {
    fun getAll(userDetail: UserDetail): List<ProductDTO>
    fun getById(ean: String, userDetail: UserDetail): ProductDTO
    fun getPurchases(ean: String, userDetail: UserDetail): List<PurchaseDTO>
    fun addProduct(productDTO: ProductDTO, userDetail: UserDetail): ProductDTO
}