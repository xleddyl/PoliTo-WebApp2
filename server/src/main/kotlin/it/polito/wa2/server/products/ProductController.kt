package it.polito.wa2.server.products

import it.polito.wa2.server.NotFoundException
import it.polito.wa2.server.NotValidException
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    private val productService: ProductService
) {

    @PostMapping("/API/products")
    fun addProduct(@RequestBody productDTO: ProductDTO?) {
        if (productDTO == null) throw NotValidException("Product was malformed")
        productService.addProduct(productDTO)
    }

    @GetMapping("/API/products")
    fun getAll(): List<ProductDTO> {
        return productService.getAll()
    }

    @GetMapping("/API/products/{ean}")
    fun getById(@PathVariable ean: String): ProductDTO? {
        val productDTO = productService.getById(ean)
        if (productDTO != null) {
            return productDTO
        }
        throw NotFoundException("Product not found")
    }
}