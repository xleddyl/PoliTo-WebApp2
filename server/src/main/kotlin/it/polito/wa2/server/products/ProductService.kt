package it.polito.wa2.server.products

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize

interface ProductService {
    fun getAll(userDetail: UserDetail): List<ProductDTO>

    fun getById(ean: String, userDetail: UserDetail): ProductDTO

    fun addProduct(productDTO: ProductDTO, userDetail: UserDetail): ProductDTO
}