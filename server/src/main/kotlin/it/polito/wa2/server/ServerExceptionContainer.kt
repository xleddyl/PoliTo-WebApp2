package it.polito.wa2.server

class NotFoundException(message: String) : RuntimeException(message)
class DuplicateException(message: String) : RuntimeException(message)
class NotValidException(message: String) : RuntimeException(message)