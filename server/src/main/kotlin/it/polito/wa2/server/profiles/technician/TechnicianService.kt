package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import org.springframework.security.access.prepost.PreAuthorize


interface TechnicianService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_customer')")
    fun getAll(): List<TechnicianDTO>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun getByEmail(email: String): TechnicianDTO?

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun addProfile(technicianDTO: TechnicianDTO): TechnicianDTO

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician')")
    fun editProfile(technicianDTO: TechnicianDTO, email: String): TechnicianDTO
}