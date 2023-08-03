package it.polito.wa2.server.security.aut

import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.profiles.UserDetail
import org.springframework.security.access.prepost.PreAuthorize

interface AuthService {
    fun createUser(userRequest: UserRequest, roles: List<String>, userDetail: UserDetail)
}