package it.polito.wa2.server.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    val CUSTOMER = "customer"

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/api/products").hasRole(CUSTOMER)
            .anyRequest().authenticated()
        http.oauth2Login(withDefaults())
        http.oauth2ResourceServer().jwt()
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }
}

// https://stackoverflow.com/questions/68316407/use-keycloak-for-only-authentication-and-use-custom-filter-for-authorization-sp
// https://www.baeldung.com/spring-boot-keycloak
// https://medium.com/geekculture/using-keycloak-with-spring-boot-3-0-376fa9f60e0b
// https://stackoverflow.com/questions/30788105/spring-security-hasrole-not-working