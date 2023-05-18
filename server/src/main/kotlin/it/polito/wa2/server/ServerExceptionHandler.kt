package it.polito.wa2.server

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ServerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_FOUND, e.message ?: "")

    @ExceptionHandler(NotValidException::class)
    fun handleNotFoundException(e: NotValidException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.message ?: "")

    @ExceptionHandler(DuplicateException::class)
    fun handleDuplicateException(e: DuplicateException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.CONFLICT, e.message ?: "")
}