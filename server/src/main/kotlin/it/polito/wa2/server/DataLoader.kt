package it.polito.wa2.server

import it.polito.wa2.server.products.Product
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.customer.CustomerProfile
import it.polito.wa2.server.profiles.customer.CustomerProfileRepository
import it.polito.wa2.server.profiles.manager.ManagerProfile
import it.polito.wa2.server.profiles.manager.ManagerProfileRepository
import it.polito.wa2.server.profiles.technician.TechnicianProfile
import it.polito.wa2.server.profiles.technician.TechnicianProfileRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataLoader(
    private val customerRepository: CustomerProfileRepository,
    private val technicianRepository: TechnicianProfileRepository,
    private val managerRepository: ManagerProfileRepository,
    private val productRepository: ProductRepository
) {

    @PostConstruct
    fun loadData() {
        customerRepository.save(CustomerProfile("Rosa Olinda", "user01@polito.it", "+00 190283947", "some address"))
        managerRepository.save(ManagerProfile("Michele Misteri", "user02@polito.it", "+00 287465392"))
        technicianRepository.save(TechnicianProfile("Pietro Piccioni", "user03@polito.it", "+00 192740387", "Company", "Specialization"))
        customerRepository.save(CustomerProfile("Giuseppe Boschetti", "user04@polito.it", "+00 985129374", "some address"))

        productRepository.save(Product("0000001", "spa-01", "Sword", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000002", "spa-02", "LightSaber", "Blacksmith&Co", "Weapons", 10.45F))
        productRepository.save(Product("0000003", "spa-03", "MachineGun", "Blacksmith&Co", "Weapons", 10.45F))
    }
}