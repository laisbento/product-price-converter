package com.centauro.product.currency.calculator.response.mock

import java.math.BigDecimal

class CurrencyResponseMock {
    companion object {

        private const val DATE = "2021-02-28"

        internal fun currencyResponseMultipleCountries(): Map<String, Map<String, BigDecimal>> {
            val dateRateIRN = mapOf(
                DATE to BigDecimal(13.14)
            )

            val dateRateUSD = mapOf(
                DATE to BigDecimal(0.18)
            )

            return mapOf(
                "BRL_IRN" to dateRateIRN,
                "BRL_USD" to dateRateUSD
            )
        }

        internal fun currencyResponseUniqueCountry(): Map<String, Map<String, BigDecimal>> {
            val dateRateUSD = mapOf(
                DATE to BigDecimal(0.18)
            )

            return mapOf(
                "BRL_USD" to dateRateUSD
            )
        }
    }
}