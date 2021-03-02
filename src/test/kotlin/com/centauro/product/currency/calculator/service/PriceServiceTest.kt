package com.centauro.product.currency.calculator.service

import com.centauro.product.currency.calculator.model.entity.Product
import com.centauro.product.currency.calculator.response.mock.CountryResponseMock.Companion.getCountries
import com.centauro.product.currency.calculator.response.mock.CurrencyResponseMock.Companion.currencyResponseMultipleCountries
import com.centauro.product.currency.calculator.response.mock.CurrencyResponseMock.Companion.currencyResponseUniqueCountry
import com.centauro.product.currency.calculator.response.mock.ProductCurrencyResponseMock.Companion.getMultipleProductCurrency
import com.centauro.product.currency.calculator.response.mock.ProductCurrencyResponseMock.Companion.productUniqueCurrencyResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class PriceServiceTest {

    @InjectMockKs
    private lateinit var priceService: PriceService

    @MockK
    private lateinit var countryService: CountryService

    @MockK
    private lateinit var currencyService: CurrencyService

    @MockK
    private lateinit var productService: ProductService

    @Test
    fun `should return current product rate when country is valid`() {
        val rates = currencyResponseUniqueCountry()
        val expectedResponse = productUniqueCurrencyResponse()
        every { productService.getProduct(any()) } returns Product(55308L, BigDecimal(129.99))
        every { countryService.getCountryCodeByName(any()) } returns "USD"
        every { currencyService.getCurrencyRate(any()) } returns rates

        val productCurrentRate = priceService.getProductCurrentRate("EUA", 55308L)

        assertEquals(listOf(expectedResponse), productCurrentRate)
        verify(exactly = 1) { productService.getProduct(any()) }
        verify(exactly = 1) { countryService.getCountryCodeByName(any()) }
        verify(exactly = 1) { currencyService.getCurrencyRate(any()) }
    }

    @Test
    fun `should return all product rates when country is null`() {
        val expectedResponse = getMultipleProductCurrency()
        val countries = getCountries()
        val rates = currencyResponseMultipleCountries()
        every { productService.getProduct(any()) } returns Product(55308L, BigDecimal(129.99))
        every { countryService.getAllCountries() } returns countries
        every { currencyService.getCurrencyRate(any()) } returns rates

        val productCurrentRate = priceService.getProductCurrentRate(null, 55308L)

        assertEquals(expectedResponse, productCurrentRate)
        verify(exactly = 1) { productService.getProduct(any()) }
        verify(exactly = 1) { countryService.getAllCountries() }
        verify(exactly = 2) { currencyService.getCurrencyRate(any()) }
    }

}