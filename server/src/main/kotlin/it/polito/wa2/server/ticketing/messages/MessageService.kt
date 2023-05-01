package it.polito.wa2.server.ticketing.messages



interface MessageService {
    fun getAllForTicket(ticketId: Long): List<MessageDTO>

    fun getById(ticketId: Long, messageId: Long): MessageDTO?

    fun addMessage(messageDTO: MessageDTO, ticketId: Long)
}