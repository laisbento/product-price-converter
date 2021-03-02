package com.centauro.product.currency.calculator.model.response

import java.time.Clock
import java.time.Instant

data class ResponseError(
    val message: String,
    val timestamp: Long? = Instant.now(Clock.systemUTC()).toEpochMilli()
)