package it.polito.wa2.server.security.aut

import org.springframework.security.oauth2.core.user.DefaultOAuth2User

interface AuthService {
    fun createUser(userRequest: UserRequest, role: String, userDetail: UserDetail)

    fun getUserDetails(user: DefaultOAuth2User?): UserDetail
}