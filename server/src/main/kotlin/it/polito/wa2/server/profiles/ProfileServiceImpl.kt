package it.polito.wa2.server.profiles

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
): ProfileService {
    override fun getByEmail(email: String): ProfileDTO? {
        return profileRepository.findByIdOrNull(email)?.toDTO()
    }

}