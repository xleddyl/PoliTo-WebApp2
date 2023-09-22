package it.polito.wa2.server.profiles.technician

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.manager.ManagerRepository
import it.polito.wa2.server.security.aut.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class TechnicianServiceImpl(
    private val technicianRepository: TechnicianRepository,
    private val managerRepository: ManagerRepository
) : TechnicianService {
    override fun getAll(userDetail: UserDetail): List<TechnicianDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può vedere tutti i technician
        return technicianRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): TechnicianDTO {
        if ((userDetail.role == UserRoles.TECHNICIAN && userDetail.email != email) || userDetail.role != UserRoles.MANAGER) throw UnauthorizedException(
            "Unauthorized"
        )  // un technician può vedere solo se stesso (il manager tutti)
        return technicianRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager
        if (technicianRepository.findByIdOrNull(technicianDTO.email) != null) throw DuplicateException("User already exists")
        val manager =
            managerRepository.findByIdOrNull(technicianDTO.managerID) ?: throw NotFoundException("Manager not found")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = manager
            )
        ).toDTO()
    }

    override fun editProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager
        if (technicianRepository.findByIdOrNull(technicianDTO.email) == null) throw NotFoundException("User not found")
        val manager =
            managerRepository.findByIdOrNull(technicianDTO.managerID) ?: throw NotFoundException("Manager not found")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = manager
            )
        ).toDTO()
    }
}