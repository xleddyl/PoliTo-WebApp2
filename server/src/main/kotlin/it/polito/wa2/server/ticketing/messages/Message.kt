package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.ticketing.tickets.Ticket
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "messages")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    var ticket: Ticket,

    var fromCustomer: Boolean,

    @Temporal(TemporalType.TIMESTAMP)
    var timestamp: Timestamp,

    @Column(columnDefinition = "text")
    var attachment: String? = null, //Base64

    var content: String = ""
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(
        id!!,
        ticket.id!!,
        fromCustomer,
        timestamp,
        attachment,
        content
    )
}