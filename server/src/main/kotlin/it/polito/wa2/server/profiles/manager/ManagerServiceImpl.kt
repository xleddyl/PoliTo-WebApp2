package it.polito.wa2.server.profiles.manager

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.toDTO
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class ManagerServiceImpl(
    private val managerRepository: ManagerRepository
) : ManagerService {
    override fun getAll(): List<ManagerDTO> {
        return managerRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String): ManagerDTO {
        return managerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(managerDTO: ManagerDTO): ManagerDTO {
        if (managerRepository.findByIdOrNull(managerDTO.email) != null) throw DuplicateException("User already exists")
        return managerRepository.save(
            Manager(
                email = managerDTO.email,
                name = managerDTO.name,
                phone = managerDTO.phone,
                level = managerDTO.level,
            )
        ).toDTO()
    }

    override fun editProfile(managerDTO: ManagerDTO, email: String): ManagerDTO {
        if (managerRepository.findByIdOrNull(email) == null) throw NotFoundException("User not found")
        return managerRepository.save(
            Manager(
                email = managerDTO.email,
                name = managerDTO.name,
                phone = managerDTO.phone,
                level = managerDTO.level,
            )
        ).toDTO()
    }
}