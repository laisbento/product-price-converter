package com.centauro.product.price.converter.service

import com.centauro.product.currency.calculator.gateway.dynamodb.CountryDynamoGateway
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CountryService(
    private val countryDynamoGateway: CountryDynamoGateway
) {

    @Cacheable(
        value = ["country"],
        key = "{#name}",
        cacheManager = "countryCacheManager"
    )
    fun getCountryCodeByName(name: String) = countryDynamoGateway.findByName(name)

    @Cacheable(
        value = ["allCountries"],
        key = "{#root.method.name}",
        cacheManager = "countryCacheManager"
    )
    fun getAllCountries() = countryDynamoGateway.findAll()
}