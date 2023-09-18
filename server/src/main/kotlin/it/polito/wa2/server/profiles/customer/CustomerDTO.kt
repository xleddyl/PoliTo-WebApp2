package it.polito.wa2.server.profiles.customer


data class CustomerDTO(
    val email: String,
    val name: String,
    val phone: String,
    val address: String,
) {
    override fun toString(): String {
        return "Customer(email='$email', name='$name', phone='$phone', address='$address')"
    }
}


