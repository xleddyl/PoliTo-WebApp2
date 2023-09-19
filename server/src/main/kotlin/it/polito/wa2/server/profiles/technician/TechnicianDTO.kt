package it.polito.wa2.server.profiles.technician

data class TechnicianDTO(
    val email: String,
    val name: String,
    val phone: String,
    val specialization: String,
    val managerID: String,
    val ticketsID: MutableSet<Long?>
) {
    override fun toString(): String {
        return "Technician(email='$email', name='$name', phone='$phone', specialization='$specialization')"
    }
}


