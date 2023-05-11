package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException


interface MessageService {
    fun getAllForTicket(ticketId: Long): List<MessageDTO>

    @Throws(NotFoundException::class)
    fun getById(ticketId: Long, messageId: Long): MessageDTO

    @Throws(DuplicateException::class)
    fun addMessage(messageDTO: MessageDTO, ticketId: Long): MessageDTO
}