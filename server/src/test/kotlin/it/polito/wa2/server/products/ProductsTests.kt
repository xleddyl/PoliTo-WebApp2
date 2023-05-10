package it.polito.wa2.server.products

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.server.LocalServerPort

class ProductsTests : AbstractApplicationTest() {

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testProduct() {
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)

        val addedProduct = restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)

        val retrievedProduct = restTemplate.getForObject("http://localhost:$port/API/products/ean",ProductDTO::class.java)

        Assertions.assertEquals(productDTO, retrievedProduct)
    }
}