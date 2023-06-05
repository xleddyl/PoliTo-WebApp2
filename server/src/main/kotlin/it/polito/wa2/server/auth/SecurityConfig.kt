package it.polito.wa2.server.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val keycloakLogoutHandler: KeycloakLogoutHandler) {
    private val CUSTOMER = "app_customer"
    private val MANAGER = "app_manager"
    private val TECHNICIAN = "app_technician"

    private val REALM_ACCESS_CLAIM = "realm_access"
    private val ROLES_CLAIM = "roles"

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/api/products*").hasRole(MANAGER)

            .requestMatchers("/api/profiles/*").hasAnyRole(MANAGER, CUSTOMER, TECHNICIAN)

            .requestMatchers(HttpMethod.GET, "/api/tickets").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            .requestMatchers(HttpMethod.POST, "/api/tickets").hasAnyRole(MANAGER, CUSTOMER)
            .requestMatchers(HttpMethod.POST, "/api/tickets/*/*").hasAnyRole(MANAGER, TECHNICIAN)
            .requestMatchers(HttpMethod.PUT, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            .requestMatchers(HttpMethod.DELETE, "/api/tickets/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)

            .requestMatchers(HttpMethod.GET, "/api/tickets/*/messages").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            .requestMatchers(HttpMethod.GET, "/api/tickets/*/messages/*").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)
            .requestMatchers(HttpMethod.POST, "/api/tickets/*/messages").hasAnyRole(MANAGER, TECHNICIAN, CUSTOMER)

            .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()

            .anyRequest().authenticated()
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