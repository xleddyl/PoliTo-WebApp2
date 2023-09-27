package it.polito.wa2.server.products

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.security.aut.AuthService
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@Observed
class ProductController(
    private val productService: ProductService,
    private val authService: AuthService,
    private val log: Logger
) {

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(
        @Valid @RequestBody productDTO: ProductDTO,
        @AuthenticationPrincipal user: DefaultOAuth2User?
    ): ProductDTO {
        log.info("ciao dal controller")
        return productService.addProduct(productDTO, authService.getUserDetails(user))
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<ProductDTO> {
        // val label = LabelMarker.of("request") { "TEST" }
        // log.info(label, "ciao")
        return productService.getAll(authService.getUserDetails(user))
    }

    @GetMapping("/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ean: String, @AuthenticationPrincipal user: DefaultOAuth2User?): ProductDTO? {
        return productService.getById(ean, authService.getUserDetails(user))
    }
}