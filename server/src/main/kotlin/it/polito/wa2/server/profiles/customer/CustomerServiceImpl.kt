package it.polito.wa2.server.profiles.customer

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.purchase.PurchaseRepository
import it.polito.wa2.server.security.aut.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val purchaseRepository: PurchaseRepository,
) : CustomerService {
    override fun getAll(userDetail: UserDetail): List<CustomerDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può vedere tutti i customer
        return customerRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): CustomerDTO {
        if ((userDetail.role == UserRoles.CUSTOMER && userDetail.email != email) || userDetail.role != UserRoles.MANAGER) throw UnauthorizedException(
            "Unauthorized"
        )  // un customer può vedere solo se stesso (il manager tutti)
        return customerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO {
        if (customerRepository.findByIdOrNull(customerDTO.email) != null) throw DuplicateException("User already exists")
        val purchases = purchaseRepository.findAllById(customerDTO.purchasesIDs).toMutableSet()
        val customer = Customer(
            email = customerDTO.email,
            name = customerDTO.name,
            phone = customerDTO.phone,
            address = customerDTO.address,
            purchases = purchases
        )
        return customerRepository.save(customer).toDTO()
    }

    override fun editProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO {
        if ((userDetail.role == UserRoles.CUSTOMER && userDetail.email != customerDTO.email) || userDetail.role != UserRoles.MANAGER) throw UnauthorizedException(
            "Unauthorized"
        )  // un customer può modificare solo se stesso (il manager tutti)
        if (customerRepository.findByIdOrNull(customerDTO.email) == null) throw NotFoundException("User not found")
        val purchases = purchaseRepository.findAllById(customerDTO.purchasesIDs).toMutableSet()
        val customer = Customer(
            email = customerDTO.email,
            name = customerDTO.name,
            phone = customerDTO.phone,
            address = customerDTO.address,
            purchases = purchases
        )
        return customerRepository.save(customer).toDTO()
    }
}
















