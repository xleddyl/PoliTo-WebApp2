package it.polito.wa2.server.profiles.technician

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TechnicianRepository : JpaRepository<Technician, String> {
    @Query("select technician from Technician technician where technician.email IN :ids")
    fun getAllByListOfId(ids: List<String>): List<Technician>
}