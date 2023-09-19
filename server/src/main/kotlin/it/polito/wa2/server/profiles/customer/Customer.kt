package it.polito.wa2.server.profiles.customer

import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.purchase.Purchase
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
class Customer(
    email: String,
    name: String,
    phone: String,

    var address: String,

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL])
    var purchases: MutableSet<Purchase> = mutableSetOf()

) : Profile(email, name, phone) {
    fun toDTO(): CustomerDTO {
        return CustomerDTO(email, name, phone, address, purchases.map { it.id }.toMutableSet())
    }
}

