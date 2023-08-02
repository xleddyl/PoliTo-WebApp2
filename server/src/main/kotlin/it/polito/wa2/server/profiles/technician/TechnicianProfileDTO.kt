package it.polito.wa2.server.profiles.technician

data class TechnicianProfileDTO(
    val email: String,
    val name: String,
    //val role: Roles,
    val phone: String,
    val companyName: String,
    val specialization: String,
) {
    fun fromDTO(): TechnicianProfile {
        return TechnicianProfile(email, name, phone, companyName, specialization)
    }
}

fun TechnicianProfile.toDTO(): TechnicianProfileDTO {
    return TechnicianProfileDTO(email, name, phone, companyName, specialization)
}
