package com.centauro.product.price.converter.service

import com.centauro.product.currency.calculator.client.CurrencyClient
import com.centauro.product.currency.calculator.exception.ExternalServiceException
import com.centauro.product.currency.calculator.response.mock.CurrencyResponseMock.Companion.currencyResponseUniqueCountry
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CurrencyServiceTest {

    @MockK
    private lateinit var currencyClient: CurrencyClient

    private val currencyService by lazy {
        CurrencyService("", currencyClient)
    }

    @Test
    fun `should get currency rate`() {
        val pairRate = currencyResponseUniqueCountry()
        every { currencyClient.getCurrencyRate(any(), any(), any()) } returns pairRate

        val currencyRate = currencyService.getCurrencyRate("BRL_USD")

        assertEquals(pairRate, currencyRate)
        verify(exactly = 1) { currencyClient.getCurrencyRate(any(), any(), any()) }
    }

    @Test
    fun `should throw exception when getting current rate fails`() {
        val externalServiceException =
            assertThrows<ExternalServiceException> { currencyService.getCurrencyRate("BRL_IRN") }

        assertEquals("Unable to get currency rate at this time. Error: ", externalServiceException.message)
        verify(exactly = 1) { currencyClient.getCurrencyRate(any(), any(), any()) }
    }
}