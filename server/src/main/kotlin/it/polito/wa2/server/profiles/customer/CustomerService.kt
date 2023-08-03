package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import org.springframework.security.access.prepost.PreAuthorize


interface CustomerService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun getAll(): List<CustomerDTO>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun getByEmail(email: String): CustomerDTO?

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun addProfile(customerDTO: CustomerDTO): CustomerDTO

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun editProfile(customerDTO: CustomerDTO, email: String): CustomerDTO
}