package it.polito.wa2.server.security.aut

import it.polito.wa2.server.DuplicateException

interface AuthService {
    @Throws(DuplicateException::class, RuntimeException::class)
    fun createUser(userRequest: UserRequest, roles: List<String>)
}