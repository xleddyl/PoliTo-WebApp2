package it.polito.wa2.server.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.SecurityFilterChain
import java.util.stream.Collectors

val CUSTOMER_APP_ROLE = "app_customer"
val CUSTOMER_APP_ROLE_ID = "b17833f4-04d7-48b3-8ecd-471141897da5"

val MANAGER_APP_ROLE = "app_manager"
val MANAGER_APP_ROLE_ID = "d1f3d15c-cb20-4a94-86c8-ecece9c1a80d"

val TECHNICIAN_APP_ROLE = "app_technician"
val TECHNICIAN_APP_ROLE_ID = "0eb7a33f-38ba-42f8-b540-58884418542b"


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val keycloakLogoutHandler: KeycloakLogoutHandler) {
    private val REALM_ACCESS_CLAIM = "realm_access"
    private val ROLES_CLAIM = "roles"

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()

            .authorizeHttpRequests()

            //.requestMatchers("/api/products*").hasRole(MANAGER)

            //.requestMatchers("/api/profiles/*").hasAnyRole(MANAGER, CUSTOMER, TECHNICIAN)

            //.requestMatchers(HttpMethod.GET, "/api/tickets").hasRole(MANAGER)
            //.requestMatchers(HttpMethod.GET, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            //.requestMatchers(HttpMethod.POST, "/api/tickets").hasAnyRole(MANAGER, CUSTOMER)
            //.requestMatchers(HttpMethod.POST, "/api/tickets/*/*").hasAnyRole(MANAGER, TECHNICIAN)
            //.requestMatchers(HttpMethod.PUT, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            //.requestMatchers(HttpMethod.DELETE, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)

            //.requestMatchers(HttpMethod.GET, "/api/tickets/*/messages").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            //.requestMatchers(HttpMethod.GET, "/api/tickets/*/messages/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            //.requestMatchers(HttpMethod.POST, "/api/tickets/*/messages").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)

            //.requestMatchers("/api/signup").permitAll()
            //.requestMatchers("/api/createExpert").hasRole(MANAGER)

            .anyRequest().permitAll()
        http.oauth2Login(withDefaults())
            .logout().addLogoutHandler(keycloakLogoutHandler)
        http.oauth2ResourceServer().jwt()
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }


    @Bean
    fun userAuthoritiesMapperForKeycloak(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities ->
            val mappedAuthorities = HashSet<GrantedAuthority>()
            val authority = authorities.iterator().next()
            val isOidc = authority is OidcUserAuthority

            if (isOidc) {
                val oidcUserAuthority = authority as OidcUserAuthority
                val userInfo = oidcUserAuthority.userInfo

                if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM)
                    val roles = realmAccess[ROLES_CLAIM] as Collection<String>
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles))
                }
            } else {
                val oauth2UserAuthority = authority as OAuth2UserAuthority
                val userAttributes = oauth2UserAuthority.attributes

                if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                    val realmAccess = userAttributes[REALM_ACCESS_CLAIM] as Map<String, Any>
                    val roles = realmAccess[ROLES_CLAIM] as Collection<String>
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles))
                }
            }
            mappedAuthorities
        }
    }

    private fun generateAuthoritiesFromClaim(roles: Collection<String>): Collection<GrantedAuthority> {
        return roles.stream().map { role -> SimpleGrantedAuthority("ROLE_$role") }.collect(Collectors.toList())
    }
}