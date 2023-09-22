package it.polito.wa2.server.purchase

import java.util.*

data class PurchaseDTO(
    val id: Long?,
    val customer: String,
    val product: String,
    val date: Date,
    val ticketID: Long?
)