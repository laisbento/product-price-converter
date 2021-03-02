package com.centauro.product.currency.calculator.model.response

import java.math.BigDecimal

data class ProductCurrencyResponse(
    val country: String,
    val price: BigDecimal
)