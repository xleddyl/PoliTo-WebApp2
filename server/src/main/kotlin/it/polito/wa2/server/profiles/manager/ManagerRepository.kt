package it.polito.wa2.server.profiles.manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository : JpaRepository<Manager, String>