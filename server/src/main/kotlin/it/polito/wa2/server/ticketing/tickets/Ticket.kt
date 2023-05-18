package it.polito.wa2.server.ticketing.tickets

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.ticketing.messages.Message
import jakarta.persistence.*

enum class States {
    OPEN, CLOSED, IN_PROGRESS, RESOLVED, REOPEN
}

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @ManyToOne(cascade = [CascadeType.ALL])
    var product: Product,
    @ManyToOne(cascade = [CascadeType.ALL])
    var customer: Profile,
    @ManyToOne(cascade = [CascadeType.ALL])
    var technician: Profile?,
    @Enumerated(value = EnumType.STRING)
    var statuses: MutableList<States> = mutableListOf(States.OPEN),
    var description: String,
    var priority: Int,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "ticket")
    var messages: MutableSet<Message>?
)