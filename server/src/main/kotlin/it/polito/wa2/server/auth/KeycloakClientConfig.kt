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
    private val clientId: String = "ticket-app-client",
    private val authUrl: String = "http://localhost:8080",
    private val realm: String = "ticketing_app"
) {

    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(authUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(realm)
            .clientId(clientId)
            .username("admin")
            .password("admin")
            .resteasyClient(
                ResteasyClientBuilder().connectionPoolSize(10).build()
            )
            .build()
    }
}