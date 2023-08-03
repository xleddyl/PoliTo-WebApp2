package it.polito.wa2.server.profiles.manager

data class ManagerDTO(
    val email: String,
    val name: String,
    val phone: String,
    val level: Int,
    val technicians: List<String>?
)


