package it.polito.wa2.server

import it.polito.wa2.server.profiles.UserRoles
import it.polito.wa2.server.security.CUSTOMER
import it.polito.wa2.server.security.MANAGER
import it.polito.wa2.server.security.TECHNICIAN
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}