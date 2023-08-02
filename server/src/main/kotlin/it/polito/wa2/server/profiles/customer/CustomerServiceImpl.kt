package it.polito.wa2.server.profiles.customer

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : CustomerService {
    override fun getByEmail(email: String): CustomerDTO {
        return customerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(customerDTO: CustomerDTO): CustomerDTO {
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

    override fun editProfile(customerDTO: CustomerDTO, email: String): CustomerDTO {
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