package it.polito.wa2.server.login

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "jwt.auth.converter")
@Configuration
@Validated
data class JwtAuthConverterProperties(
    var resourceId: String? = null,
    var principalAttribute: String? = null
)