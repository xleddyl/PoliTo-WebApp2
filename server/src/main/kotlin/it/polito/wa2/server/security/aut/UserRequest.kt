package it.polito.wa2.server.security.aut

data class UserRequest(
    val username: String,
    val password: String
)