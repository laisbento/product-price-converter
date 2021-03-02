package com.centauro.product.currency.calculator.controller

import com.centauro.product.currency.calculator.service.PriceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/price/convert")
class PriceConverterController(
    private val priceService: PriceService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getProductConvertedPrices(@RequestParam(required = false) country: String?, @RequestParam productCode: Long) =
        priceService.getProductCurrentRate(country, productCode)

}