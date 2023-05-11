package it.polito.wa2.server.products

import it.polito.wa2.server.AbstractApplicationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

class ProductsTests : AbstractApplicationTest() {
    @Test
    fun testProduct() {
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)

        val retrievedProduct = restTemplate.getForObject("http://localhost:$port/API/products/ean",ProductDTO::class.java)

        Assertions.assertEquals(productDTO, retrievedProduct)
    }

    @Test
    fun testGetProduct() {
        val productDTO = ProductDTO("ean", "sku", "name", "brand", "category", 1.0f)
        val productDTOsecond = ProductDTO("ean1", "sku", "name", "brand", "category", 1.0f)
        val productDTOthird = ProductDTO("ean2", "sku", "name", "brand", "category", 1.0f)

        restTemplate.postForLocation("http://localhost:$port/API/products", productDTO)
        restTemplate.postForLocation("http://localhost:$port/API/products", productDTOsecond)
        restTemplate.postForLocation("http://localhost:$port/API/products", productDTOthird)

        val rateResponse: ResponseEntity<List<ProductDTO>> = restTemplate.exchange("http://localhost:$port/API/products",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProductDTO>>() {})
        val rates: List<ProductDTO>? = rateResponse.getBody()

        if (rates != null) {
            Assertions.assertEquals(rates.size, 3)
        }
    }
}