package it.polito.wa2.server.profiles

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException


interface ProfileService {
    @Throws(NotFoundException::class)
    fun getByEmail(email: String): ProfileDTO

    @Throws(DuplicateException::class)
    fun addProfile(profileDTO: ProfileDTO): ProfileDTO

    @Throws(NotFoundException::class)
    fun editProfile(profileDTO: ProfileDTO, email: String): ProfileDTO
}