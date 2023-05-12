package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException


interface MessageService {
    fun getAllForTicket(ticketId: Long): List<Message>

    @Throws(NotFoundException::class)
    fun getById(ticketId: Long, messageId: Long): Message

    @Throws(DuplicateException::class)
    fun addMessage(messageDTO: MessageDTO, ticketId: Long): Message
}