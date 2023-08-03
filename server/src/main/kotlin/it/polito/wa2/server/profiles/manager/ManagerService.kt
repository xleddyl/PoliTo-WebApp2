package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import org.springframework.security.access.prepost.PreAuthorize


interface ManagerService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun getAll(): List<ManagerDTO>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager')")
    fun getByEmail(email: String): ManagerDTO?

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager')")
    fun addProfile(managerDTO: ManagerDTO): ManagerDTO

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager')")
    fun editProfile(managerDTO: ManagerDTO, email: String): ManagerDTO
}