package it.polito.wa2.server.profiles.customer

data class CustomerProfileDTO(
    val email: String,
    val name: String,
    //val role: Roles,
    val phone: String,
    val address: String
) {
    fun fromDTO(): CustomerProfile {
        return CustomerProfile(email, name, phone, address)
    }
}

fun CustomerProfile.toDTO(): CustomerProfileDTO {
    return CustomerProfileDTO(email, name, phone, address)
}
