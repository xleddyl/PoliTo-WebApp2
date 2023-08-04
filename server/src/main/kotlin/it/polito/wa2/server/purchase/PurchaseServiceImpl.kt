package it.polito.wa2.server.purchase

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.security.aut.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : PurchaseService {
    override fun getAll(userDetail: UserDetail): List<PurchaseDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")
        return purchaseRepository.findAll().map { it.toDTO() }
    }

    override fun getAllByEmail(email: String, userDetail: UserDetail): List<PurchaseDTO> {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può vedere gli acquisti dei customer
        if (userDetail.role == UserRoles.CUSTOMER || userDetail.email != email) throw UnauthorizedException("Unauthorized") // un customer può vedere solo i propri acquisti

        return purchaseRepository.findByCustomerEmail(email).map { it.toDTO() }
    }

    override fun addPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può aggiungere acquisti ai customer
        if (userDetail.role == UserRoles.CUSTOMER && userDetail.email != purchaseDTO.customer) throw UnauthorizedException(
            "Unauthorized"
        ) // un customer può aggiungere acquisti solo a se stesso

        return purchaseRepository.save(
            Purchase(
                customer = customerRepository.findByIdOrNull(purchaseDTO.customer)
                    ?: throw NotValidException("Customer does not exists"),
                product = productRepository.findByIdOrNull(purchaseDTO.product)
                    ?: throw NotValidException("Product does not exists"),
                date = purchaseDTO.date,
            )
        ).toDTO()
    }
}