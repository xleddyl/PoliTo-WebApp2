package it.polito.wa2.server.profiles.customer

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
    private val ticketRepository: TicketRepository
) : CustomerService {
    override fun getAll(userDetail: UserDetail): List<CustomerDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può vedere tutti i customer

        return customerRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): CustomerDTO {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può vedere i customer
        if (userDetail.role == UserRoles.CUSTOMER || userDetail.email != email) throw UnauthorizedException("Unauthorized") // un customer può vedere solo se stesso

        return customerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può aggiungere i customer
        if (userDetail.role == UserRoles.CUSTOMER || userDetail.email != customerDTO.email) throw UnauthorizedException(
            "Unauthorized"
        ) // un customer può aggiungere solo se stesso

        if (customerRepository.findByIdOrNull(customerDTO.email) != null) throw DuplicateException("${customerDTO.email} already exists")
        return customerRepository.save(
            Customer(
                email = customerDTO.email,
                name = customerDTO.name,
                phone = customerDTO.phone,
                address = customerDTO.address,
                products = productRepository.getAllByListOfId(customerDTO.products ?: emptyList()).toMutableSet(),
                tickets = ticketRepository.getAllByListOfId(customerDTO.tickets ?: emptyList()).toMutableSet(),
            )
        ).toDTO()
    }

    override fun editProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO {
        if (userDetail.role == UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized") // un technician non può modificare i customer
        if (userDetail.role == UserRoles.CUSTOMER || userDetail.email != customerDTO.email) throw UnauthorizedException(
            "Unauthorized"
        ) // un customer può modificare solo se stesso

        if (customerRepository.findByIdOrNull(customerDTO.email) == null) throw NotFoundException("${customerDTO.email} not found")
        return customerRepository.save(
            Customer(
                email = customerDTO.email,
                name = customerDTO.name,
                phone = customerDTO.phone,
                address = customerDTO.address,
                products = productRepository.getAllByListOfId(customerDTO.products ?: emptyList()).toMutableSet(),
                tickets = ticketRepository.getAllByListOfId(customerDTO.tickets ?: emptyList()).toMutableSet(),
            )
        ).toDTO()
    }
}
















