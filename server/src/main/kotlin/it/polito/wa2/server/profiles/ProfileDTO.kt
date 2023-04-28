package it.polito.wa2.server.profiles

data class ProfileDTO(
    val email: String,
    val name: String,
    val role: Roles,
    val phone: String
)

fun Profile.toDTO(): ProfileDTO {
    return ProfileDTO(email, name, role, phone)
}
