package it.polito.wa2.server

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.customer.Customer
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.profiles.manager.Manager
import it.polito.wa2.server.profiles.manager.ManagerRepository
import it.polito.wa2.server.profiles.technician.Technician
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataLoader(
    private val productRepository: ProductRepository,
    private val customerRepository: CustomerRepository,
    private val technicianRepository: TechnicianRepository,
    private val managerRepository: ManagerRepository,
) {

    @PostConstruct
    fun loadData() {
        customerRepository.save(Customer("user01@polito.it", "Rosa Olinda", "+00 190283947", "via dalla galera 12"))
        managerRepository.save(Manager("user02@polito.it", "Michele Misteri", "+00 287465392", 1))
        technicianRepository.save(Technician("user03@polito.it", "Pietro Piccioni", "+00 192740387", "poesie"))
        customerRepository.save(Customer("user04@polito.it", "Giuseppe Boschetti", "+00 985129374", "via degli ignoti 9"))

        productRepository.save(Product("0000001", "spa-01", "Sword", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000002", "spa-02", "LightSaber", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000003", "spa-03", "MachineGun", "Blacksmith&Co", "Weapons", 10.45F))
    }
}