package it.polito.wa2.server.profiles


import it.polito.wa2.server.DTONotValidException
import it.polito.wa2.server.DuplicateProfileException
import it.polito.wa2.server.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ProfilesErrorHandler {
    @RestControllerAdvice
    class ProblemDetailsHandler : ResponseEntityExceptionHandler() {
        @ExceptionHandler(ProfileNotFoundException::class)
        fun handleProfileNotFound(e: ProfileNotFoundException) = ProblemDetail
            .forStatusAndDetail(HttpStatus.NOT_FOUND, e.message!!)

        @ExceptionHandler(DTONotValidException::class)
        fun handleDTONotValid(e: DTONotValidException) = ProblemDetail
            .forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.message!!)

        @ExceptionHandler(DuplicateProfileException::class)
        fun handleDuplicateProfile(e: DuplicateProfileException) = ProblemDetail
            .forStatusAndDetail(HttpStatus.CONFLICT, e.message!!)
    }
}