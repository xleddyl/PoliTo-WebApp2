package it.polito.wa2.server.profiles.manager

data class ManagerDTO(
    val email: String,
    val name: String,
    val phone: String,
    val level: Int,
    val techniciansID: MutableSet<String>
) {

    override fun toString(): String {
        return "Manager(email='$email', name='$name', phone='$phone', level=$level)"
    }
}


