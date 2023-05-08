package it.polito.wa2.server.products

import it.polito.wa2.server.AbstractApplicationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

class ProductsTests : AbstractApplicationTest() {

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate
}