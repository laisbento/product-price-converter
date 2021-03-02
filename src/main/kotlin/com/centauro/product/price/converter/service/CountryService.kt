package com.centauro.product.price.converter.service

import com.centauro.product.price.converter.exception.ExternalServiceException
import com.centauro.product.price.converter.gateway.dynamodb.CountryDynamoGateway
import com.centauro.product.price.converter.model.request.CountryRequest
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CountryService(
    private val countryDynamoGateway: CountryDynamoGateway
) {

    @Cacheable(
        value = ["country"],
        key = "{#countryId}",
        cacheManager = "countryCacheManager"
    )
    fun getCountryById(countryId: UUID) = countryDynamoGateway.findById(countryId)

    @Cacheable(
        value = ["allCountries"],
        key = "{#root.method.name}",
        cacheManager = "countryCacheManager"
    )
    fun getAllCountries() = countryDynamoGateway.findAll()

    @CacheEvict(
        cacheNames = ["allCountries", "country"],
        cacheManager = "countryCacheManager",
        allEntries = true
    )
    fun createCountry(countryRequest: CountryRequest): CountryRequest {
        try {
            countryDynamoGateway.createCountry(countryRequest)
            return countryRequest
        } catch (ex: Exception) {
            throw ExternalServiceException("Unable to create a country at this moment. Error: ", ex)
        }
    }

    @CacheEvict(
        cacheNames = ["allCountries", "country"],
        cacheManager = "countryCacheManager",
        allEntries = true
    )
    fun deleteCountry(countryId: UUID) {
        this.getCountryById(countryId)

        try {
            countryDynamoGateway.deleteCountry(countryId)
        } catch (ex: Exception) {
            throw ExternalServiceException("Unable to delete a country at this moment. Error: ", ex)
        }
    }
}