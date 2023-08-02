package it.polito.wa2.server.profiles.manager

data class ManagerProfileDTO(
    val email: String,
    val name: String,
    //val role: Roles,
    val phone: String
) {
    fun fromDTO(): ManagerProfile {
        return ManagerProfile(email, name, phone)
    }
}

fun ManagerProfile.toDTO(): ManagerProfileDTO {
    return ManagerProfileDTO(email, name, phone)
}
