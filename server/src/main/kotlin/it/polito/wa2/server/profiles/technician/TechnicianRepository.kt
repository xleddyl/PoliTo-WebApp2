package it.polito.wa2.server.profiles.technician

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TechnicianRepository : JpaRepository<Technician, String>