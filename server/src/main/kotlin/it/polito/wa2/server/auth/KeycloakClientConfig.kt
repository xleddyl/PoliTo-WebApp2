package it.polito.wa2.server.auth

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakClientConfig (
    @Value("\${keycloak.resource}")
    private val clientId: String,
    @Value("\${keycloak.auth-server-url}")
    private val authUrl: String,
    @Value("\${keycloak.realm}")
    private val realm: String
) {

    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .serverUrl(authUrl)
            .realm(realm)
            .clientId(clientId)
            .build()
    }
}