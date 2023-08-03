package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize


interface ManagerService {
    fun getAll(userDetail: UserDetail): List<ManagerDTO>

    fun getByEmail(email: String, userDetail: UserDetail): ManagerDTO?

    fun addProfile(managerDTO: ManagerDTO, userDetail: UserDetail): ManagerDTO

    fun editProfile(managerDTO: ManagerDTO, email: String, userDetail: UserDetail): ManagerDTO
}