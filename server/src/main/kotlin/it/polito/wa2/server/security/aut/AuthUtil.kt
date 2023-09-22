package it.polito.wa2.server.security.aut

import it.polito.wa2.server.profiles.UserRoles

data class UserRequest(
    val username: String, // keycloak
    val email: String, // keycloak + profile
    val firstName: String, // keycloak
    val lastName: String, // keycloak
    val password: String, // keycloak
    val phone: String, // keycloak + profile
    val address: String, // keycloak
    val purchasesIDs: MutableSet<Long>?, // customer
    val specialization: String?, // technician
    val managerID: String?, // technician
    val ticketsIDs: MutableSet<Long>?, // technician
    val level: Int?, // manager
    val techniciansIDs: MutableSet<String>?, // manager
)

data class UserDetail(
    val role: UserRoles,
    val email: String
)

