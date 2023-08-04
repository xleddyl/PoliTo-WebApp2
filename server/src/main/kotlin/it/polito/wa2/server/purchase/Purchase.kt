package it.polito.wa2.server.purchase

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.profiles.customer.Customer
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "purchases")
class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "customer_email")
    var customer: Customer,
    @ManyToOne
    @JoinColumn(name = "product_ean")
    var product: Product,
    @Temporal(value = TemporalType.DATE)
    var date: Date
)

fun Purchase.toDTO(): PurchaseDTO {
    return PurchaseDTO(id, customer.email, product.ean, date)
}