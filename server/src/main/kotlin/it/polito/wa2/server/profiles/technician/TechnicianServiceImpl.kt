package it.polito.wa2.server.profiles.technician

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.products.toDTO
import it.polito.wa2.server.profiles.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class TechnicianServiceImpl(
    private val technicianRepository: TechnicianRepository
) : TechnicianService {
    override fun getAll(userDetail: UserDetail): List<TechnicianDTO> {
        return technicianRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): TechnicianDTO {
        return technicianRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (technicianRepository.findByIdOrNull(technicianDTO.email) != null) throw DuplicateException("User already exists")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization
            )
        ).toDTO()
    }

    override fun editProfile(technicianDTO: TechnicianDTO, email: String, userDetail: UserDetail): TechnicianDTO {
        if (technicianRepository.findByIdOrNull(email) == null) throw NotFoundException("User not found")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization
            )
        ).toDTO()
    }
}