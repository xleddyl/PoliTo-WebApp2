package it.polito.wa2.server.profiles

import it.polito.wa2.server.DuplicateProfileException
import it.polito.wa2.server.ProfileNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
): ProfileService {
    override fun getByEmail(email: String): ProfileDTO? {
        return profileRepository.findByIdOrNull(email)?.toDTO()
    }

    override fun addProfile(profileDTO: ProfileDTO) {
        if (profileRepository.findByIdOrNull(profileDTO.email) != null) {
            throw DuplicateProfileException("User already exist with the same email address")
        }
        profileRepository.save(Profile(
            email = profileDTO.email,
            name = profileDTO.name,
            role = profileDTO.role,
            phone = profileDTO.phone
        ))
    }

    override fun editProfile(profileDTO: ProfileDTO, email: String) {
        val profile = profileRepository.findByIdOrNull(email)
        if (profile == null) {
            throw ProfileNotFoundException("No user associated with this email address")
        }
        profileRepository.save(Profile(
            email = email,
            name = profileDTO.name,
            role = profileDTO.role,
            phone = profileDTO.phone
        ))
    }
}