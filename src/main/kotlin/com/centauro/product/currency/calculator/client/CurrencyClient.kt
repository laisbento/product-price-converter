package com.centauro.product.currency.calculator.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal

@FeignClient(name = "currency-api-client", url = "\${feign.client.currency-api}")
interface CurrencyClient {

    @GetMapping(value = ["/convert?compact=ultra"], consumes = ["application/json"])
    fun getCurrencyRate(
        @RequestParam("q") pair: String,
        @RequestParam apiKey: String,
        @RequestParam date: String
    ): Map<String, Map<String, BigDecimal>>

}