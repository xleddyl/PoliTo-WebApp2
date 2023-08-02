package it.polito.wa2.server.profiles.technician

import it.polito.wa2.server.profiles.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TechnicianProfileRepository : JpaRepository<Profile, String>