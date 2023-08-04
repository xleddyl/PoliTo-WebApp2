package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.purchase.Purchase
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
    @OneToMany(mappedBy = "customer")
    var purchases: MutableSet<Purchase> = mutableSetOf(),
    @OneToMany(mappedBy = "customer")
    var tickets: MutableSet<Ticket> = mutableSetOf()
) : Profile(email, name, phone)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(email, name, phone, address, purchases.map { it.id!! }, tickets.map { it.id!! })
}