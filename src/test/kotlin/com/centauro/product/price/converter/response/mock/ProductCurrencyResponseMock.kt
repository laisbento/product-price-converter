package com.centauro.product.price.converter.response.mock

import com.centauro.product.currency.calculator.model.response.ProductCurrencyResponse
import java.math.BigDecimal
import java.math.RoundingMode

class ProductCurrencyResponseMock {

    companion object {
        internal fun getMultipleProductCurrency() = listOf(
            ProductCurrencyResponse("EUA", BigDecimal(23.40).setScale(2, RoundingMode.CEILING)),
            ProductCurrencyResponse("Índia", BigDecimal(1708.07).setScale(2, RoundingMode.CEILING)),
        )

        internal fun productUniqueCurrencyResponse() =
            ProductCurrencyResponse("EUA", BigDecimal(23.40).setScale(2, RoundingMode.CEILING))
    }
}