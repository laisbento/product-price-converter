package com.centauro.product.price.converter.model.response

import java.time.Clock
import java.time.Instant

data class ResponseError(
    val message: String,
    val timestamp: Long? = Instant.now(Clock.systemUTC()).toEpochMilli()
)