package it.polito.wa2.server.profiles

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
            TODO("ERROR -> duplicate email")
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
            TODO("ERROR -> email does not exists")
        }
        profileRepository.save(Profile(
            email = email,
            name = profileDTO.name,
            role = profileDTO.role,
            phone = profileDTO.phone
        ))
    }
}