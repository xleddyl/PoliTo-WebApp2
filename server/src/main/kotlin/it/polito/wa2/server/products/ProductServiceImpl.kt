package it.polito.wa2.server.products

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.server.DuplicateException
import it.polito.wa2.server.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
@Observed
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun getAll(): List<ProductDTO> {
        return productRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ean: String): ProductDTO {
        return productRepository.findByIdOrNull(ean)?.toDTO() ?: throw NotFoundException("Product not found")
    }

    override fun addProduct(productDTO: ProductDTO): ProductDTO {
        if (productRepository.findByIdOrNull(productDTO.ean) != null) throw DuplicateException("Product already exist")
        return productRepository.save(
            productDTO.fromDTO()
        ).toDTO()
    }
}