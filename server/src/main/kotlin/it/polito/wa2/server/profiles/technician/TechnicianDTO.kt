package it.polito.wa2.server.profiles.technician

data class TechnicianDTO(
    val email: String,
    val name: String,
    val phone: String,
    val specialization: String,
    val tickets: List<Long>?,
    val manager: String
)


