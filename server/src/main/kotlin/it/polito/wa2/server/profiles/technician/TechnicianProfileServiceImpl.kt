package it.polito.wa2.server.profiles.technician

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.profiles.Profile
import it.polito.wa2.server.profiles.ProfileDTO
import it.polito.wa2.server.profiles.ProfileRepository
import it.polito.wa2.server.profiles.customer.toDTO
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class TechnicianProfileServiceImpl(
    private val profileRepository: ProfileRepository
) : TechnicianProfileService {
    override fun getByEmail(email: String): ProfileDTO {
        return profileRepository.findByIdOrNull(email)?.toDTO() ?: throw NotFoundException("User not found")
    }

    override fun getByEmailP(email: String): Profile {
        return profileRepository.findByIdOrNull(email) ?: throw NotFoundException("User not found")
    }

    override fun addProfile(profileDTO: ProfileDTO): ProfileDTO {
        if (profileRepository.findByIdOrNull(profileDTO.email) != null) throw DuplicateException("User already exists")
        return profileRepository.save(
            Profile(
                email = profileDTO.email,
                name = profileDTO.name,
                //role = profileDTO.role,
                phone = profileDTO.phone
            )
        ).toDTO()
    }

    override fun editProfile(profileDTO: ProfileDTO, email: String): ProfileDTO {
        if (profileRepository.findByIdOrNull(email) == null) throw NotFoundException("User not found")
        return profileRepository.save(
            Profile(
                email = email,
                name = profileDTO.name,
                //role = profileDTO.role,
                phone = profileDTO.phone
            )
        ).toDTO()
    }
}