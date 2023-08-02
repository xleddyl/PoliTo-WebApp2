package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import org.springframework.security.access.prepost.PreAuthorize


interface MessageService {
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun getAllForTicket(ticketId: Long): List<Message>

    @Throws(NotFoundException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun getById(ticketId: Long, messageId: Long): Message

    @Throws(DuplicateException::class)
    @PreAuthorize("hasRole('app_manager') or hasRole('app_technician') or hasRole('app_customer')")
    fun addMessage(messageDTO: MessageDTO, ticketId: Long): Message
}