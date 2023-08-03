package it.polito.wa2.server

class NotFoundException(message: String) : RuntimeException(message)
class DuplicateException(message: String) : RuntimeException(message)
class NotValidException(message: String) : RuntimeException(message)
class UnauthorizedException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)