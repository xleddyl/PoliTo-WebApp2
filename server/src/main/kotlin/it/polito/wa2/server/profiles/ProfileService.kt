package it.polito.wa2.server.profiles

interface ProfileService {
    fun getByEmail(email: String): ProfileDTO?
}