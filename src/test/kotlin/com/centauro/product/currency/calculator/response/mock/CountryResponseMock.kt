package com.centauro.product.currency.calculator.response.mock

import com.centauro.product.currency.calculator.model.entity.Country
import java.util.*

class CountryResponseMock {

    companion object {
        internal fun getCountries() = listOf(
            Country(UUID.randomUUID(), "USD", "EUA"),
            Country(UUID.randomUUID(), "IRN", "Índia")
        )
    }
}