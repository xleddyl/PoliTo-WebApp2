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
import it.polito.wa2.server.ticketing.tickets.States
import it.polito.wa2.server.ticketing.tickets.Ticket
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import org.springframework.stereotype.Component
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
) {

    @PostConstruct
    fun loadData() {
        val customer1 = customerRepository.save(Customer("user01@polito.it", "Rosa Olinda", "+00 190283947", "via dalla galera 12"))
        val manager = managerRepository.save(Manager("user02@polito.it", "Michele Misteri", "+00 287465392", 1))
        val technician = technicianRepository.save(Technician("user03@polito.it", "Pietro Piccioni", "+00 192740387", "poesie", manager=manager))
        val customer2 = customerRepository.save(Customer("user04@polito.it", "Giuseppe Boschetti", "+00 985129374", "via degli ignoti 9"))

        val product1 = productRepository.save(Product("0000001", "spa-01", "Sword", "Blacksmith&Co", "Weapons", 10.45F))
        val product2 = productRepository.save(Product("0000002", "spa-02", "LightSaber", "Blacksmith&Co", "Weapons", 10.45F))
        val product3 = productRepository.save(Product("0000003", "spa-03", "MachineGun", "Blacksmith&Co", "Weapons", 10.45F))

        val purchase = purchaseRepository.save(Purchase(customer = customer1, product = product1, date = Date(1235995800000L)))

        //val ticket = ticketRepository.save(Ticket(technician = technician, statuses = mutableListOf(States.OPEN), description = "Product Broken", priority = 2, messages = mutableSetOf()))
    }
}