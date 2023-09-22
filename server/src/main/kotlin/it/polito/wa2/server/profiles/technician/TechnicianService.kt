package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.security.aut.UserDetail


interface TechnicianService {
    fun getAll(userDetail: UserDetail): List<TechnicianDTO>

    fun getByEmail(email: String, userDetail: UserDetail): TechnicianDTO

    fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO

    fun editProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO
}