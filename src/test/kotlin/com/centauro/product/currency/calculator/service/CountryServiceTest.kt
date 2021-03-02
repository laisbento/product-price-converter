package com.centauro.product.currency.calculator.service

import com.centauro.product.currency.calculator.gateway.dynamodb.CountryDynamoGateway
import com.centauro.product.currency.calculator.model.entity.Country
import com.centauro.product.currency.calculator.response.mock.CountryResponseMock.Companion.getCountries
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class CountryServiceTest {

    @InjectMockKs
    private lateinit var countryService: CountryService

    @MockK
    private lateinit var countryDynamoGateway: CountryDynamoGateway

    @Test
    fun `should get country code by name`() {
        val usd = "USD"
        every { countryDynamoGateway.findByName(any()) } returns usd

        val countryCodeByName = countryService.getCountryCodeByName("EUA")

        assertEquals(usd, countryCodeByName)
        verify(exactly = 1) { countryDynamoGateway.findByName(any()) }
    }

    @Test
    fun `should return all countries available`() {
        val countries = getCountries()
        every { countryDynamoGateway.findAll() } returns countries

        val allCountries = countryService.getAllCountries()

        assertEquals(countries, allCountries)
        verify(exactly = 1) { countryDynamoGateway.findAll() }
    }

}