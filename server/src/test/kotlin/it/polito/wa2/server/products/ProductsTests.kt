package it.polito.wa2.server.products

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext

class ProductsTests : AbstractApplicationTest() {
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test create product`() {
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)

        val retrievedProduct = restTemplate.getForObject("http://localhost:$port/API/products/ean", ProductDTO::class.java)

        Assertions.assertEquals(productDTO, retrievedProduct)
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    fun `test get all products`() {
        val productDTO1 = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)
        val productDTO2 = ProductDTO("ean1", "sku", "name", "brand", "category", 1.0f)
        val productDTO3 = ProductDTO("ean2", "sku", "name", "brand", "category", 1.0f)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO1)
        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO2)
        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO3)

        val rateResponse: ResponseEntity<List<ProductDTO>> = restTemplate.exchange("http://localhost:$port/API/products",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProductDTO>>() {})
        val rates: List<ProductDTO>? = rateResponse.getBody()

        if (rates != null) {
            Assertions.assertEquals(rates.size, 3)
        }
    }
}