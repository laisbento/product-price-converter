package com.centauro.product.currency.calculator.model.entity

import java.io.Serializable
import java.math.BigDecimal

data class Product(
    val code: Long,
    val price: BigDecimal
): Serializable