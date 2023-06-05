package it.polito.wa2.server.auth

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakClientConfig(
    @Value("\${keycloak.clientId}")
    private val clientId: String,
    @Value("\${keycloak.authUrl}")
    private val authUrl: String,
    @Value("\${keycloak.realm}")
    private val realm: String,
    @Value("\${keycloak.secret}")
    private val secret: String
) {

    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .serverUrl(authUrl)
            .realm(realm)
            .clientId(clientId)
            .clientSecret(secret)
            .build()
    }
}