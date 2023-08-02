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
        customerRepository.save(Customer("Rosa Olinda", "user01@polito.it", "+00 190283947", "via dalla galera 12"))
        managerRepository.save(Manager("Michele Misteri", "user02@polito.it", "+00 287465392", 1))
        technicianRepository.save(Technician("Pietro Piccioni", "user03@polito.it", "+00 192740387", "poesie"))
        customerRepository.save(
            Customer(
                "Giuseppe Boschetti",
                "user04@polito.it",
                "+00 985129374",
                "via degli ignoti 9"
            )
        )

        productRepository.save(Product("0000001", "spa-01", "Sword", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000002", "spa-02", "LightSaber", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000003", "spa-03", "MachineGun", "Blacksmith&Co", "Weapons", 10.45F))
    }
}