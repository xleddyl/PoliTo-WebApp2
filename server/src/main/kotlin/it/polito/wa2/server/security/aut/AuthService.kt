package it.polito.wa2.server.security.aut

import it.polito.wa2.server.DuplicateException
import org.springframework.security.access.prepost.PreAuthorize

interface AuthService {
    @Throws(DuplicateException::class, RuntimeException::class)
    @PreAuthorize("hasRole('app_manager')")
    fun createUser(userRequest: UserRequest, roles: List<String>)
}