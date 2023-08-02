package it.polito.wa2.server

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ServerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        d.title = "Not Found"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(NotValidException::class)
    fun handleNotFoundException(e: NotValidException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        d.title = "Unprocessable Entity"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(DuplicateException::class)
    fun handleDuplicateException(e: DuplicateException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.CONFLICT)
        d.title = "Conflict"
        d.detail = e.message
        return d
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        d.title = "Internal Server Error"
        d.detail = e.message
        return d
    }
}