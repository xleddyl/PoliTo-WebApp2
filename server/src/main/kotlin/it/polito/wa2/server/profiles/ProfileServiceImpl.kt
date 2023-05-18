package it.polito.wa2.server.profiles

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
) : ProfileService {
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
                role = profileDTO.role,
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
                role = profileDTO.role,
                phone = profileDTO.phone
            )
        ).toDTO()
    }
}