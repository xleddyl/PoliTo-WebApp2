package it.polito.wa2.server.ticketing.messages

import it.polito.wa2.server.security.aut.UserDetail


interface MessageService {

    fun getAllForTicket(ticketId: Long, userDetail: UserDetail): List<MessageDTO>

    fun getById(ticketId: Long, messageId: Long, userDetail: UserDetail): MessageDTO

    fun addMessage(messageDTO: MessageDTO, ticketId: Long, userDetail: UserDetail): MessageDTO
}