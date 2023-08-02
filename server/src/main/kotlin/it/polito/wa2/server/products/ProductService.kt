package it.polito.wa2.server.products

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import org.springframework.security.access.prepost.PreAuthorize

interface ProductService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer') or hasRole('app_technician')")
    fun getAll(): List<ProductDTO>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer') or hasRole('app_technician')")
    fun getById(ean: String): ProductDTO

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager')")
    fun addProduct(productDTO: ProductDTO): ProductDTO
}