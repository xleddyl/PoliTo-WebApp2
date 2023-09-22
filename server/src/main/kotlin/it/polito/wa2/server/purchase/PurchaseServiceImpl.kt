package it.polito.wa2.server.purchase

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.customer.CustomerRepository
import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
    private val ticketRepository: TicketRepository
) : PurchaseService {
    override fun getAll(userDetail: UserDetail): List<PurchaseDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")
        return purchaseRepository.findAll().map { it.toDTO() }
    }

    override fun getAllByListOfID(list: List<Long>, userDetail: UserDetail): List<PurchaseDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")
        return purchaseRepository.findAllById(list).map { it.toDTO() }
    }

    override fun getAllByEmail(email: String, userDetail: UserDetail): List<PurchaseDTO> {
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException(
            "Unauthorized"
        )
        if (userDetail.role == UserRoles.CUSTOMER && userDetail.email != email) throw UnauthorizedException("Unauthorized")
        return purchaseRepository.findByCustomerEmail(email).map { it.toDTO() }
    }

    override fun addPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO {
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException(
            "Unauthorized"
        )
        val customer = customerRepository.findByIdOrNull(purchaseDTO.customer)
            ?: throw NotValidException("Customer does not exists")
        val product =
            productRepository.findByIdOrNull(purchaseDTO.product) ?: throw NotValidException("Product does not exists")
        return purchaseRepository.save(
            Purchase(
                customer = customer,
                product = product,
                date = purchaseDTO.date,
                // ticket null -> does not exists yet
            )
        ).toDTO()
    }

    override fun editPurchase(purchaseDTO: PurchaseDTO, userDetail: UserDetail): PurchaseDTO {
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException(
            "Unauthorized"
        )
        if (purchaseRepository.findByIdOrNull(purchaseDTO.id) == null) throw NotFoundException("Purchase not found")
        val customer = customerRepository.findByIdOrNull(purchaseDTO.customer)
            ?: throw NotValidException("Customer does not exists")
        val product =
            productRepository.findByIdOrNull(purchaseDTO.product) ?: throw NotValidException("Product does not exists")
        val ticket = ticketRepository.findByIdOrNull(purchaseDTO.ticketID)
        return purchaseRepository.save(
            Purchase(
                customer = customer,
                product = product,
                date = purchaseDTO.date,
                ticket = ticket
            )
        ).toDTO()
    }
}