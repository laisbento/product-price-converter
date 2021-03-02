package com.centauro.product.price.converter.exception.handler

import com.centauro.product.price.converter.exception.CurrencyNotAvailableException
import com.centauro.product.price.converter.exception.EntityNotFoundException
import com.centauro.product.price.converter.exception.ExternalServiceException
import com.centauro.product.price.converter.model.response.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(entityNotFoundException: EntityNotFoundException) =
        entityNotFoundException.message?.let { ResponseError(it) }

    @ExceptionHandler(ExternalServiceException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    fun handleExternalServiceException(externalServiceException: ExternalServiceException) =
        externalServiceException.message?.let { ResponseError(it) }

    @ExceptionHandler(CurrencyNotAvailableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCurrencyNotAvailableException(currencyNotAvailableException: CurrencyNotAvailableException) =
        currencyNotAvailableException.message?.let { ResponseError(it) }
}