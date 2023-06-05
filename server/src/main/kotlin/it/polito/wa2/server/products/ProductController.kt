package it.polito.wa2.server.products

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.NotValidException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
//@Observed
class ProductController(
    private val productService: ProductService
) {
    //val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@RequestBody productDTO: ProductDTO?): ProductDTO {
        if (productDTO == null) throw NotValidException("Product was malformed")
        return productService.addProduct(productDTO)
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ean: String): ProductDTO? {
        return productService.getById(ean)
    }
}