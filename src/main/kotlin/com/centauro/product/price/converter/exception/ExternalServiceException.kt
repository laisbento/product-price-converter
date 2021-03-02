package com.centauro.product.price.converter.exception

class ExternalServiceException(message: String, ex: Exception) : RuntimeException(message, ex)