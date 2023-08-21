package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.purchase.PurchaseDTO
import it.polito.wa2.server.security.aut.UserDetail


interface CustomerService {
    fun getAll(userDetail: UserDetail): List<CustomerDTO>

    fun getByEmail(email: String, userDetail: UserDetail): CustomerDTO

    fun getPurchases(email: String, userDetail: UserDetail): List<PurchaseDTO>

    fun addProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO

    fun editProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO
}