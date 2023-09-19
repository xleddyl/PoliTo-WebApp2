package it.polito.wa2.server.profiles.technician

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.manager.ManagerDTO
import it.polito.wa2.server.profiles.manager.ManagerRepository
import it.polito.wa2.server.security.aut.UserDetail
import it.polito.wa2.server.ticketing.tickets.TicketDTO
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
        if (userDetail.role == UserRoles.TECHNICIAN && userDetail.email != email) throw UnauthorizedException("Unauthorized") // un technician può vedere solo se stesso
        return technicianRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun getManager(email: String, userDetail: UserDetail): ManagerDTO {
        if (userDetail.role != UserRoles.MANAGER && userDetail.role != UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized")
        if (userDetail.role == UserRoles.TECHNICIAN && userDetail.email != email ) throw UnauthorizedException("Unauthorized") // un technician può vedere solo se stesso

        return technicianRepository.findByIdOrNull(email)?.manager?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun getTickets(email: String, userDetail: UserDetail): List<TicketDTO> {
        if (userDetail.role != UserRoles.MANAGER && userDetail.role != UserRoles.TECHNICIAN) throw UnauthorizedException("Unauthorized")
        if (userDetail.role == UserRoles.TECHNICIAN && userDetail.email != email ) throw UnauthorizedException("Unauthorized") // un technician può vedere solo se stesso

        return technicianRepository.findByIdOrNull(email)?.tickets?.map { it.toDTO() } ?: throw NotFoundException("User not found")
    }

    override fun addProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized")
        if (technicianRepository.findByIdOrNull(technicianDTO.email) != null) throw DuplicateException("User already exists")
        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = managerRepository.findByIdOrNull(userDetail.email)!!

            )
        ).toDTO()
    }

    override fun editProfile(technicianDTO: TechnicianDTO, userDetail: UserDetail): TechnicianDTO {
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login
        if (userDetail.role == UserRoles.CUSTOMER) throw UnauthorizedException("Unauthorized") // un customer non può modificare i technician
        if (userDetail.role == UserRoles.TECHNICIAN || userDetail.email != technicianDTO.email) throw UnauthorizedException(
            "Unauthorized"
        ) // un technician può modificare solo se stesso
        val oldTechnician = technicianRepository.findByIdOrNull(technicianDTO.email)
            ?: throw NotFoundException("User not found")

        return technicianRepository.save(
            Technician(
                email = technicianDTO.email,
                name = technicianDTO.name,
                phone = technicianDTO.phone,
                specialization = technicianDTO.specialization,
                manager = managerRepository.findByIdOrNull(userDetail.email) ?: oldTechnician.manager
            )
        ).toDTO()
    }
}