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
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login
        if (userDetail.role == UserRoles.CUSTOMER) throw UnauthorizedException("Unauthorized") // un customer non può vedere i technician
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.email != email) throw UnauthorizedException("Unauthorized") // un technician può vedere solo se stesso
        return technicianRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login
        if (userDetail.role == UserRoles.CUSTOMER) throw UnauthorizedException("Unauthorized") // un customer non può aggiungere i technician
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.email != technicianDTO.email) throw UnauthorizedException(
            "Unauthorized"
        ) // un technician può aggiungere solo se stesso
        if (technicianRepository.findByIdOrNull(technicianDTO.email) != null) throw DuplicateException("User already exists")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = managerRepository.findByIdOrNull(technicianDTO.manager)
                    ?: throw throw NotFoundException("Manager not found")
            )
        ).toDTO()
    }

    override fun editProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login
        if (userDetail.role == UserRoles.CUSTOMER) throw UnauthorizedException("Unauthorized") // un customer non può modificare i technician
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.email != technicianDTO.email) throw UnauthorizedException(
            "Unauthorized"
        ) // un technician può modificare solo se stesso
        if (technicianRepository.findByIdOrNull(technicianDTO.email) == null) throw NotFoundException("User not found")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = managerRepository.findByIdOrNull(technicianDTO.manager)
                    ?: throw throw NotFoundException("Manager not found")
            )
        ).toDTO()
    }
}