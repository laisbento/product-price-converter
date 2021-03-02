package com.centauro.product.currency.calculator.exception

class ExternalServiceException(message: String, ex: Exception) : RuntimeException(message, ex)