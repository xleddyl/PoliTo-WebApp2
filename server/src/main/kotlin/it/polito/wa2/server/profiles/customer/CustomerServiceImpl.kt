package it.polito.wa2.server.profiles.customer

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.UserDetail
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.manager.ManagerDTO
import it.polito.wa2.server.profiles.manager.toDTO
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import javax.ws.rs.BadRequestException

@Service
@Transactional
@Observed
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
) : CustomerService {
    override fun getAll(userDetail: UserDetail): List<CustomerDTO> {
        return customerRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): CustomerDTO {
        return if ((userRole == UserRoles.CUSTOMER /* && email == userEmail */) || userRole == UserRoles.MANAGER) {
            customerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
        } else throw BadRequestException()

    }

    override fun addProfile(customerDTO: CustomerDTO, userDetail: UserDetail): CustomerDTO {
        if (customerRepository.findByIdOrNull(customerDTO.email) != null) throw DuplicateException("User already exists")
        return customerRepository.save(
            Customer(
                email = customerDTO.email,
                name = customerDTO.name,
                phone = customerDTO.phone,
                address = customerDTO.address
            )
        ).toDTO()
    }

    override fun editProfile(customerDTO: CustomerDTO, email: String, userDetail: UserDetail): CustomerDTO {
        if (customerRepository.findByIdOrNull(email) == null) throw NotFoundException("User not found")
        return customerRepository.save(
            Customer(
                email = customerDTO.email,
                name = customerDTO.name,
                phone = customerDTO.phone,
                address = customerDTO.address
            )
        ).toDTO()
    }
}