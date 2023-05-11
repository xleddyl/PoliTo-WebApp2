package it.polito.wa2.server.products

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ProductsTests : AbstractApplicationTest() {
    @Test
    fun `test create product`() {
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)

        val retrievedProduct = restTemplate.getForObject("http://localhost:$port/API/products/ean", ProductDTO::class.java)

        Assertions.assertEquals(productDTO, retrievedProduct)
    }
}