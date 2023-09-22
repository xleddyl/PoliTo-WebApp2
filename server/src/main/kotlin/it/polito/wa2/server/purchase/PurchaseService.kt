package it.polito.wa2.server.purchase

import it.polito.wa2.server.security.aut.UserDetail

interface PurchaseService {
    fun getAll(userDetail: UserDetail): List<PurchaseDTO>

    fun getAllByListOfID(list: List<Long>, userDetail: UserDetail): List<PurchaseDTO>

    fun getAllByEmail(email: String, userDetail: UserDetail): List<PurchaseDTO>

    fun addPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO

    fun editPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO
}