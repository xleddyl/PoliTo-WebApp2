package it.polito.wa2.server.products

import it.polito.wa2.server.ProductNotFoundException
import it.polito.wa2.server.DuplicateProductException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ProductsErrorHandler {
    @RestControllerAdvice
    class ProblemDetailsHandler: ResponseEntityExceptionHandler() {
        @ExceptionHandler(ProductNotFoundException::class)
        fun handleProductNotFound(e: ProductNotFoundException) = ProblemDetail
            .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )
        @ExceptionHandler(DuplicateProductException::class)
        fun handleDuplicateProduct(e: DuplicateProductException) = ProblemDetail
            .forStatusAndDetail(HttpStatus.CONFLICT, e.message!! )
    }
}