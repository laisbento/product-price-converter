package com.centauro.product.currency.calculator.model.entity

import java.io.Serializable
import java.util.*

data class Country(
    val id: UUID,
    val code: String,
    val name: String
): Serializable