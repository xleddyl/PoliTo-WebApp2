package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.ticketing.tickets.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
class Customer(
    email: String, name: String, phone: String,
    var address: String,
    @ManyToMany(mappedBy = "customers")
    var products: MutableSet<Product> = mutableSetOf(),
    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile(email, name, phone)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(email, name, phone, address, products.map { it.ean }, tickets.map { it.id!! })
}