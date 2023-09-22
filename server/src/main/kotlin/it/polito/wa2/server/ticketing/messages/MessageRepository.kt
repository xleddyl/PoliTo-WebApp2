package it.polito.wa2.server.ticketing.messages

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageRepository : JpaRepository<Message, Long?> {
    @Query("select message from Message message where message.ticket.id = :ticketId")
    fun findMessagesByTicketId(ticketId: Long): List<Message>

    @Query("select message from Message message where message.id = :id and message.ticket.id = :ticketId")
    fun findMessageByIdAndTicketId(id: Long, ticket: Long): Message?
}