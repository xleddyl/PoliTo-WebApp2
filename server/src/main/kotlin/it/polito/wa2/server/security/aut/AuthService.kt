package it.polito.wa2.server.security.aut

interface AuthService {
    fun createUser(userRequest: UserRequest, roles: List<String>, userDetail: UserDetail)
}