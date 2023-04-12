package it.polito.wa2.server

class ProductNotFoundException(message: String) : RuntimeException(message);
class DuplicateProductException(message: String) : RuntimeException(message);
class ProfileNotFoundException(message: String) : RuntimeException(message);
class DTONotValidException(message: String) : RuntimeException(message);
class DuplicateProfileException(message: String) : RuntimeException(message);