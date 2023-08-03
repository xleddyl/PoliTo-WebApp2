package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize


interface TechnicianService {
    fun getAll(userDetail: UserDetail): List<TechnicianDTO>

    fun getByEmail(email: String, userDetail: UserDetail): TechnicianDTO?

    fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO

    fun editProfile(technicianDTO: TechnicianDTO, email: String, userDetail: UserDetail): TechnicianDTO
}