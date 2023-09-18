package it.polito.wa2.server.purchase

import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketDTO

interface PurchaseService {
    fun getAll(userDetail: UserDetail): List<PurchaseDTO>

    fun getAllByEmail(email: String, userDetail: UserDetail): List<PurchaseDTO>

    fun getPurchaseTicket(id: Long, userDetail: UserDetail): TicketDTO?

    fun addPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO
}