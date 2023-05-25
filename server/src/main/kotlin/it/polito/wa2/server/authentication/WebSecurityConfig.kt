package it.polito.wa2.server.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    @Autowired private val jwtAuthConverter: JwtAuthConverter
) {


    val MANAGER = "app_manager"
    val EXPERT = "app_expert"
    val USER = "app_user"

    
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.authorizeHttpRequests()

            .requestMatchers(HttpMethod.POST, "/login/").permitAll()
            .requestMatchers(HttpMethod.POST, "/signup/").permitAll()
            .requestMatchers(HttpMethod.POST, "/createExpert/").permitAll()

            .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/message/**").hasAnyRole(USER, EXPERT)
            .requestMatchers(HttpMethod.POST, "/message/**").hasRole(USER)
            .requestMatchers(HttpMethod.POST, "/ticket/**").hasRole(USER)

            .requestMatchers(HttpMethod.POST, "/profiles/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/profiles/**").hasRole(USER)
            .requestMatchers(HttpMethod.PUT, "/profiles/**").hasRole(USER)

            .requestMatchers(HttpMethod.POST, "/expert/**").hasRole(EXPERT)
            .requestMatchers(HttpMethod.PUT, "/expert/**").hasRole(EXPERT)

            .requestMatchers(HttpMethod.PUT, "/manager/**").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/manager/**").hasRole(MANAGER)

                .anyRequest().authenticated()

        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }
}

