package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import it.polito.wa2.server.profiles.UserRoles
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User


interface CustomerService {
    fun getAll(userDetail: UserDetail): List<CustomerDTO>

    fun getByEmail(email: String, userDetail: UserDetail): CustomerDTO?

    fun addProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO

    fun editProfile(customerDTO: CustomerDTO, email: String, userDetail: UserDetail): CustomerDTO
}