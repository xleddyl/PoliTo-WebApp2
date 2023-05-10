package it.polito.wa2.server.products

import it.polito.wa2.server.DuplicateException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun getAll(): List<ProductDTO> {
        return productRepository.findAll().map { it.toDTO() }
    }

    override fun getById(ean: String): ProductDTO? {
        return productRepository.findByIdOrNull(ean)?.toDTO()
    }

    override fun addProduct(productDTO: ProductDTO) {
        if (productRepository.findByIdOrNull(productDTO.ean) != null) throw DuplicateException("Product already exist")
        productRepository.save(
            productDTO.fromDTO()
        )
    }
}