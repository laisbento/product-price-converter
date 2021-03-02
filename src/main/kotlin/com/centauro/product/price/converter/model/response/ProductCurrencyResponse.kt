package com.centauro.product.price.converter.model.response

import java.math.BigDecimal

data class ProductCurrencyResponse(
    val country: String,
    val price: BigDecimal
)