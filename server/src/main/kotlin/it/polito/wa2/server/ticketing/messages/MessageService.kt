package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize


interface MessageService {
    fun getAllForTicket(ticketId: Long, userDetail: UserDetail): List<Message>

    fun getById(ticketId: Long, messageId: Long, userDetail: UserDetail): Message

    fun addMessage(messageDTO: MessageDTO, ticketId: Long, userDetail: UserDetail): Message
}