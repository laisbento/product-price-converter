package com.centauro.product.currency.calculator.service

import com.centauro.product.currency.calculator.client.CurrencyClient
import com.centauro.product.currency.calculator.exception.ExternalServiceException
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class CurrencyService(
    @Value("\${feign.client.api-key}") private val apiKey: String,
    private val currencyClient: CurrencyClient
) {
    @Cacheable(
        value = ["currency-rate"],
        key = "{#pair}",
        cacheManager = "currencyRateCacheManager"
    )
    fun getCurrencyRate(pair: String): Map<String, Map<String, BigDecimal>> {
        try {
            val date = LocalDate.now().minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return currencyClient.getCurrencyRate(pair, apiKey, date)
        } catch (ex: Exception) {
            throw ExternalServiceException("Unable to get currency rate at this time. Error: ", ex)
        }
    }
}