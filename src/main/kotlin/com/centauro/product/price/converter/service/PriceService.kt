package com.centauro.product.price.converter.service

import com.centauro.product.price.converter.model.response.ProductCurrencyResponse
import org.springframework.stereotype.Service
import java.math.RoundingMode
import java.util.*

@Service
class PriceService(
    private val countryService: CountryService,
    private val currencyService: CurrencyService,
    private val productService: ProductService
) {

    companion object {
        const val BRL = "BRL_"
    }

    fun getProductCurrentRate(countryId: UUID?, productCode: Long): Collection<ProductCurrencyResponse> {
        val productPrice = productService.getProduct(productCode).price

        return countryId?.let {
            val code = countryService.getCountryById(it)
            val pairCode = BRL.plus(code.code)

            val currencyRate = currencyService.getCurrencyRate(pairCode)[pairCode]!!.values.first()

            return@let listOf(
                ProductCurrencyResponse(
                    country = code.name,
                    price = (currencyRate * productPrice).setScale(2, RoundingMode.CEILING)
                )
            )
        } ?: run {
            return@run countryService.getAllCountries().map { country ->
                val pairCode = BRL.plus(country.code)
                val currencyRate = currencyService.getCurrencyRate(pairCode)[pairCode]!!.values.first()
                return@map ProductCurrencyResponse(
                    country = country.name,
                    price = (currencyRate * productPrice).setScale(2, RoundingMode.CEILING)
                )
            }
        }
    }
}
