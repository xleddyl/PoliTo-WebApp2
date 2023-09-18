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
import it.polito.wa2.server.ticketing.tickets.States
import it.polito.wa2.server.ticketing.tickets.Ticket
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.hibernate.Hibernate
import org.springframework.data.repository.findByIdOrNull
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
        val customer1 = customerRepository.save(Customer("user01@polito.it", "Rosa Olinda", "+00 190283947", "via dalla galera 12"))
        val manager = managerRepository.save(Manager("user02@polito.it", "Michele Misteri", "+00 287465392", 1))
        val technician = technicianRepository.save(Technician("user03@polito.it", "Pietro Piccioni", "+00 192740387", "poesie", manager=manager))
        val customer2 = customerRepository.save(Customer("user04@polito.it", "Giuseppe Boschetti", "+00 985129374", "via degli ignoti 9"))

        val product1 = productRepository.save(Product("0000001", "spa-01", "Sword", "Blacksmith&Co", "Weapons", 10.45F))
        val product2 = productRepository.save(Product("0000002", "spa-02", "LightSaber", "Blacksmith&Co", "Weapons", 10.45F))
        val product3 = productRepository.save(Product("0000003", "spa-03", "MachineGun", "Blacksmith&Co", "Weapons", 10.45F))

        val purchase = purchaseRepository.save(
            Purchase(
                customer = customer1,
                product = product1,
                date = Date(1695028957917L)
            )
        )


        val ticket = ticketRepository.save(
            Ticket(
                statuses = mutableListOf(States.OPEN),
                description = "Product Broken",
                priority = 2,
                purchase = purchase,
                technician = technician
            )
        )

        purchase.ticket = ticket
        purchaseRepository.save(purchase)


        val message1 = messageRepository.save(
            Message(
                ticket = ticket,
                fromCustomer = true,
                new = true,
                content = "My sword is broken",
                timestamp = Timestamp.valueOf(LocalDateTime.now())
            )
        )

    }

    @PostConstruct
    fun testData() {
        println("\n\n\n\tPrinting data from repositories:")

        // Retrieve and print data from each repository
        println("\tCustomers:")
        customerRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tManagers:")
        managerRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tTechnicians:")
        technicianRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tProducts:")
        productRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tPurchases:")
        purchaseRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tTickets:")
        ticketRepository.findAll().forEach { println("\t${it.toDTO()}") }

        println("\tMessages:")
        messageRepository.findAll().forEach { println("\t${it.toDTO()}") }

    }
}