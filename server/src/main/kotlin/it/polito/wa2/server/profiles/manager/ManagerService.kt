package it.polito.wa2.server.profiles.manager

import it.polito.wa2.server.security.aut.UserDetail


interface ManagerService {
    fun getAll(userDetail: UserDetail): List<ManagerDTO>

    fun getByEmail(email: String, userDetail: UserDetail): ManagerDTO

    fun addProfile(managerDTO: ManagerDTO, userDetail: UserDetail): ManagerDTO

    fun editProfile(managerDTO: ManagerDTO, userDetail: UserDetail): ManagerDTO
}