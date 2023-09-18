package it.polito.wa2.server.purchase

import java.util.Date

data class PurchaseDTO(
    val id: Long,
    val customer: String,
    val product: String,
    val date: Date,
) {
    override fun toString(): String {
        return "Purchase(id=$id, customer='${customer}', product='${product}', date=$date)"
    }
}