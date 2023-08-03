package it.polito.wa2.server.profiles.manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository : JpaRepository<Manager, String> {
    @Query("select manager from Manager manager where manager.email IN :ids")
    fun getAllByListOfId(ids: List<String>): List<Manager>
}