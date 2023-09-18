package it.polito.wa2.server.profiles.manager

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.UnauthorizedException
import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.profiles.technician.TechnicianDTO
import it.polito.wa2.server.profiles.technician.TechnicianRepository
import it.polito.wa2.server.security.aut.UserDetail
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class ManagerServiceImpl(
    private val managerRepository: ManagerRepository,
) : ManagerService {
    override fun getAll(userDetail: UserDetail): List<ManagerDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può vedere tutti i manager

        return managerRepository.findAll().map { it.toDTO() }
    }

    override fun getByEmail(email: String, userDetail: UserDetail): ManagerDTO {
        if (userDetail.role == UserRoles.NO_AUTH) throw UnauthorizedException("Unauthorized") // no login
        if (userDetail.role == UserRoles.CUSTOMER) throw UnauthorizedException("Unauthorized") // solo un customer non può vedere i manager

        return managerRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun getTechnicians(email: String, userDetail: UserDetail): List<TechnicianDTO> {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo manager vede i technician

        return managerRepository.findByIdOrNull(email)?.technicians?.map { it.toDTO() } ?: throw NotFoundException("User not found")
    }

    override fun addProfile(managerDTO: ManagerDTO, userDetail: UserDetail): ManagerDTO {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può aggiungere un manager

        if (managerRepository.findByIdOrNull(managerDTO.email) != null) throw DuplicateException("User already exists")

        val manager = Manager(
            email = managerDTO.email,
            name = managerDTO.name,
            phone = managerDTO.phone,
            level = managerDTO.level,
        )
        //manager.technicians = technicianRepository.getAllByListOfId(managerDTO.technicians ?: emptyList()).toMutableSet()
        return managerRepository.save(manager).toDTO()
    }

    override fun editProfile(managerDTO: ManagerDTO, userDetail: UserDetail): ManagerDTO {
        if (userDetail.role != UserRoles.MANAGER) throw UnauthorizedException("Unauthorized") // solo un manager può modificare un manager

        if (managerRepository.findByIdOrNull(managerDTO.email) == null) throw NotFoundException("User not found")

        val manager = Manager(
            email = managerDTO.email,
            name = managerDTO.name,
            phone = managerDTO.phone,
            level = managerDTO.level,
        )
        //manager.technicians = technicianRepository.getAllByListOfId(managerDTO.technicians ?: emptyList()).toMutableSet()
        return managerRepository.save(manager).toDTO()
    }
}