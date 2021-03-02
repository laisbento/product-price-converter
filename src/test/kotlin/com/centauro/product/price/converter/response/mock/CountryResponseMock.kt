package com.centauro.product.price.converter.response.mock

import com.centauro.product.price.converter.model.entity.Country
import java.util.*

class CountryResponseMock {

    companion object {
        internal fun getCountries() = listOf(
            Country(UUID.randomUUID(), "USD", "EUA"),
            Country(UUID.randomUUID(), "IRN", "Índia")
        )

        internal fun getUniqueCountry() =
            Country(UUID.randomUUID(), "USD", "EUA")
    }
}