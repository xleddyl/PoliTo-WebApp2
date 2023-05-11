package it.polito.wa2.server.products

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
        private val productService: ProductService
) {

    @PostMapping("/API/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@RequestBody productDTO: ProductDTO?): ProductDTO {
        if (productDTO == null) throw NotValidException("Product was malformed")
        return productService.addProduct(productDTO)
    }

    @GetMapping("/API/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/API/products/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable ean: String): ProductDTO? {
        val productDTO = productService.getById(ean)
        if (productDTO != null) {
            return productDTO
        }
        throw NotFoundException("Product not found")
    }
}