package it.polito.wa2.server.login

import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class JwtAuthConverter : Converter<Jwt,AbstractAuthenticationToken>{

    private val jwtGrantedAuthoritiesConverter: JwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
    private var properties: JwtAuthConverterProperties= JwtAuthConverterProperties()


    constructor(properties: JwtAuthConverterProperties) {
        this.properties = properties
    }

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val authorities = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(jwt)?.stream(),
            extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet<GrantedAuthority>())

        return JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt))
    }

    fun extractResourceRoles(jwt: Jwt): Set<GrantedAuthority> {
        val resourceAccess: Map<String, Any>? = jwt.getClaim("resource_access")
        val resource = resourceAccess?.get(properties.resourceId) as? Map<String, Any>?


        val resourceRoles = resource?.get("roles") as? Collection<String>
        print(resourceAccess)
        print(resource)
        print(resourceRoles)
        if (resourceAccess==null || resource==null ||resourceRoles==null){
            return emptySet()
        }
        return resourceRoles?.stream()
            ?.map { role -> SimpleGrantedAuthority("ROLE_$role") }
            ?.collect(Collectors.toSet()) ?: emptySet()
    }


    private fun getPrincipalClaimName(jwt: Jwt): String {
        var claimName: String? = JwtClaimNames.SUB
        if (properties.principalAttribute != null) {
            claimName = properties.principalAttribute
        }
        return jwt.getClaim(claimName)}
}