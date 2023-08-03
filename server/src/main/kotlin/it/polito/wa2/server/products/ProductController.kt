package it.polito.wa2.server.products

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@Valid @RequestBody productDTO: ProductDTO, @AuthenticationPrincipal user: DefaultOAuth2User?): ProductDTO {
        return productService.addProduct(productDTO)
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@AuthenticationPrincipal user: DefaultOAuth2User?): List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ean: String, @AuthenticationPrincipal user: DefaultOAuth2User?): ProductDTO? {
        return productService.getById(ean)
    }
}