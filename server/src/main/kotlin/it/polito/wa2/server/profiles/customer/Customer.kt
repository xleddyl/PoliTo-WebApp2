package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.profiles.Profile
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "profiles_customer")
class Customer(
    email: String, name: String, phone: String,
    var address: String
) : Profile(email, name, phone)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(email, name, phone, address)
}

fun CustomerDTO.fromDTO(): Customer {
    return Customer(email, name, phone, address)
}