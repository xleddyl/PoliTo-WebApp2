package it.polito.wa2.server

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.customer.Customer
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.profiles.manager.Manager
import it.polito.wa2.server.profiles.manager.ManagerRepository
import it.polito.wa2.server.profiles.technician.Technician
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import it.polito.wa2.server.purchase.Purchase
import it.polito.wa2.server.purchase.PurchaseRepository
import it.polito.wa2.server.ticketing.messages.Message
import it.polito.wa2.server.ticketing.messages.MessageRepository
import it.polito.wa2.server.ticketing.tickets.Statuses
import it.polito.wa2.server.ticketing.tickets.Ticket
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

@Component
class DataLoader(
    private val productRepository: ProductRepository,
    private val customerRepository: CustomerRepository,
    private val technicianRepository: TechnicianRepository,
    private val managerRepository: ManagerRepository,
    private val purchaseRepository: PurchaseRepository,
    private val ticketRepository: TicketRepository,
    private val messageRepository: MessageRepository
) {

    @PostConstruct
    @Transactional
    fun loadData() {
        val manager = managerRepository.save(Manager("user02@polito.it", "Michele Misteri", "+00 287465392", 1))
        val technician = technicianRepository.save(
            Technician(
                "user03@polito.it",
                "Pietro Piccioni",
                "+00 192740387",
                "poesie",
                manager = manager
            )
        )

        val customer1 =
            customerRepository.save(Customer("user01@polito.it", "Rosa Olinda", "+00 190283947", "via dalla galera 12"))
        val customer2 = customerRepository.save(
            Customer(
                "user04@polito.it",
                "Giuseppe Boschetti",
                "+00 985129374",
                "via degli ignoti 9"
            )
        )

        val product01 =
            productRepository.save(Product("1345346763", "spa-01", "Smartphone", "Apple", "Smartphones", 10.45F))
        val product02 =
            productRepository.save(Product("4965664982", "spa-02", "Laptop", "Microsoft", "Computer", 10.45F))
        val product03 =
            productRepository.save(Product("5423253453", "spa-03", "Tablet", "Samsung", "Tablet", 10.45F))
        val product04 =
            productRepository.save(Product("7856234567", "spa-04", "Smartwatch", "Google", "Wearables", 15.99F))
        val product05 = productRepository.save(
            Product(
                "3254678912",
                "spa-05",
                "Smart TV",
                "LG",
                "TV",
                29.99F
            )
        )
        val product06 =
            productRepository.save(Product("9876543210", "spa-06", "Console", "Sony", "Gaming", 25.50F))
        val product07 =
            productRepository.save(Product("1234567890", "spa-07", "Router", "Asus", "Networking", 39.99F))
        val product08 =
            productRepository.save(Product("4567890123", "spa-08", "NAS", "Synology", "Storage", 199.99F))
        val product09 =
            productRepository.save(Product("5678901234", "spa-09", "Smart Speaker", "Amazon", "Smart Home", 42.75F))
        val product10 = productRepository.save(
            Product(
                "6789012345",
                "spa-10",
                "Virtual Reality Headset",
                "Meta",
                "Gaming",
                299.99F
            )
        )
        val product11 =
            productRepository.save(Product("7890123456", "spa-11", "Wireless Earbuds", "Apple", "Audio", 12.99F))
        val product12 =
            productRepository.save(Product("8901234567", "spa-12", "Smartwatch Band", "Samsung", "Wearables", 19.95F))
        val product13 =
            productRepository.save(Product("9012345678", "spa-13", "Smartphone Case", "Spigen", "Accessories", 29.99F))


        val purchase01 =
            purchaseRepository.save(Purchase(customer = customer1, product = product01, date = Date(1695028957917L)))
        val purchase02 =
            purchaseRepository.save(Purchase(customer = customer2, product = product02, date = Date(1695028957918L)))
        val purchase03 =
            purchaseRepository.save(Purchase(customer = customer1, product = product03, date = Date(1695028957919L)))
        val purchase04 =
            purchaseRepository.save(Purchase(customer = customer2, product = product04, date = Date(1695028957920L)))
        val purchase05 =
            purchaseRepository.save(Purchase(customer = customer1, product = product05, date = Date(1695028957921L)))
        val purchase06 =
            purchaseRepository.save(Purchase(customer = customer2, product = product06, date = Date(1695028957922L)))

        val ticket01 = ticketRepository.save(
            Ticket(
                statuses = mutableListOf(Statuses.OPEN),
                description = "Product Broken",
                priority = 2,
                purchase = purchase01,
                technician = technician
            )
        )
        val ticket02 = ticketRepository.save(
            Ticket(
                statuses = mutableListOf(Statuses.OPEN),
                description = "Product Broken",
                priority = 2,
                purchase = purchase04,
                technician = technician
            )
        )
        val ticket03 = ticketRepository.save(
            Ticket(
                statuses = mutableListOf(Statuses.OPEN),
                description = "Product Malfunctioning",
                priority = 1,
                purchase = purchase02,
                technician = technician
            )
        )
        val ticket04 = ticketRepository.save(
            Ticket(
                statuses = mutableListOf(Statuses.OPEN),
                description = "PLEASE HELP",
                priority = 1,
                purchase = purchase03,
            )
        )

        purchase01.ticket = ticket01
        purchase04.ticket = ticket02
        purchase02.ticket = ticket03
        purchase03.ticket = ticket04
        purchaseRepository.save(purchase01)
        purchaseRepository.save(purchase04)
        purchaseRepository.save(purchase02)
        purchaseRepository.save(purchase03)

        val message01 = messageRepository.save(
            Message(
                ticket = ticket01,
                fromCustomer = true,
                content = "My device is broken",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )
        val message02 = messageRepository.save(
            Message(
                ticket = ticket01,
                fromCustomer = false,
                content = "I will assign a technician to fix it.",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )
        val message03 = messageRepository.save(
            Message(
                ticket = ticket01,
                fromCustomer = true,
                content = "Thank you!",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )

        val message04 = messageRepository.save(
            Message(
                ticket = ticket02,
                fromCustomer = true,
                content = "My device is malfunctioning.",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )
        val message05 = messageRepository.save(
            Message(
                ticket = ticket02,
                fromCustomer = false,
                content = "We will send a technician to inspect it.",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )
        val message06 = messageRepository.save(
            Message(
                ticket = ticket02,
                fromCustomer = true,
                content = "I appreciate your prompt response.",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )
    }
}