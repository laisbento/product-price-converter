package com.centauro.product.price.converter.service

import com.centauro.product.price.converter.exception.CurrencyNotAvailableException
import com.centauro.product.price.converter.exception.ExternalServiceException
import com.centauro.product.price.converter.gateway.dynamodb.CountryDynamoGateway
import com.centauro.product.price.converter.model.entity.Country
import com.centauro.product.price.converter.model.request.CountryRequest
import com.centauro.product.price.converter.response.mock.CountryResponseMock.Companion.getCountries
import com.centauro.product.price.converter.response.mock.CountryResponseMock.Companion.getUniqueCountry
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class CountryServiceTest {

    @InjectMockKs
    private lateinit var countryService: CountryService

    @MockK
    private lateinit var countryDynamoGateway: CountryDynamoGateway

    @Test
    fun `should get country code by id`() {
        val id = UUID.randomUUID()
        val country = Country(id = id, code = "USD", name = "EUA")
        every { countryDynamoGateway.findById(any()) } returns country

        val countryCodeByName = countryService.getCountryById(id)

        assertEquals(country, countryCodeByName)
        verify(exactly = 1) { countryDynamoGateway.findById(any()) }
    }

    @Test
    fun `should return all countries available`() {
        val countries = getCountries()
        every { countryDynamoGateway.findAll() } returns countries

        val allCountries = countryService.getAllCountries()

        assertEquals(countries, allCountries)
        verify(exactly = 1) { countryDynamoGateway.findAll() }
    }

    @Test
    fun `should create a new country`() {
        val country = CountryRequest("GBP", "Reino Unido")
        every { countryDynamoGateway.createCountry(any()) } just runs

        val createdCountry = countryService.createCountry(country)

        assertEquals(country, createdCountry)
        verify(exactly = 1) { countryDynamoGateway.createCountry(any()) }
    }

    @Test
    fun `should delete a country`() {
        val country = getUniqueCountry()
        every { countryDynamoGateway.findById(any()) } returns country
        every { countryDynamoGateway.deleteCountry(any()) } just runs

        countryService.deleteCountry(country.id)

        verify(exactly = 1) { countryDynamoGateway.findById(any()) }
        verify(exactly = 1) { countryDynamoGateway.deleteCountry(any()) }
    }

    @Test
    fun `should throw exception when creating a new country is not available`() {
        val externalServiceException =
            assertThrows<ExternalServiceException> { countryService.createCountry(CountryRequest("", "")) }

        assertEquals("Unable to create a country at this moment. Error: ", externalServiceException.message)
        verify(exactly = 1) { countryDynamoGateway.createCountry(any()) }
    }

    @Test
    fun `should throw exception when deleting a country is not available`() {
        every { countryService.getCountryById(any()) } returns getUniqueCountry()
        val externalServiceException =
            assertThrows<ExternalServiceException> { countryService.deleteCountry(UUID.randomUUID()) }

        assertEquals("Unable to delete a country at this moment. Error: ", externalServiceException.message)
        verify(exactly = 1) { countryDynamoGateway.deleteCountry(any()) }
    }

    @Test
    fun `should throw exception when getting country id is invalid`() {
        val countryId = UUID.randomUUID()
        every { countryDynamoGateway.findById(any()) } throws CurrencyNotAvailableException("Currency from country with id $countryId is not available")

        val currencyNotAvailableException =
            assertThrows<CurrencyNotAvailableException> { countryService.getCountryById(countryId) }

        assertEquals("Currency from country with id $countryId is not available", currencyNotAvailableException.message)
        verify(exactly = 1) { countryDynamoGateway.findById(any()) }
    }


}