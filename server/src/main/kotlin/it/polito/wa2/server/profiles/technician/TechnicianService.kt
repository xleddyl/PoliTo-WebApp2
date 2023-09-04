package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.profiles.manager.ManagerDTO
import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketDTO


interface TechnicianService {
    fun getAll(userDetail: UserDetail): List<TechnicianDTO>

    fun getAllPending(userDetail: UserDetail): List<TechnicianDTO>

    fun getByEmail(email: String, userDetail: UserDetail): TechnicianDTO

    fun getManager(email: String, userDetail: UserDetail): ManagerDTO

    fun getTickets(email: String, userDetail: UserDetail): List<TicketDTO>

    fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO

    fun editProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO
}