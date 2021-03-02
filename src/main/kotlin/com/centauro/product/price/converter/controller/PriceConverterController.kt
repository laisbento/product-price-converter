package com.centauro.product.price.converter.controller

import com.centauro.product.currency.calculator.model.response.ProductCurrencyResponse
import com.centauro.product.currency.calculator.model.response.ResponseError
import com.centauro.product.currency.calculator.service.PriceService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api(
    tags = ["Product Price Converter"],
    description = "API responsible for retrieving a product price based on a country or not"
)
@RestController
@RequestMapping("/v1/price/convert")
class PriceConverterController(
    private val priceService: PriceService
) {

    @ApiOperation("Get product price converted based on a country (or not)")
    @ApiResponses(
        ApiResponse(
            code = 202,
            message = "Product price",
            response = ProductCurrencyResponse::class,
            responseContainer = "List"
        ),
        ApiResponse(
            code = 503,
            message = "Failed to get an information from an external service",
            response = ResponseError::class
        ),
        ApiResponse(
            code = 404,
            message = "Product doesn't exist. Check its code",
            response = ResponseError::class
        ),
        ApiResponse(
            code = 400,
            message = "Currency not available for checking rate",
            response = ResponseError::class
        )
    )
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getProductConvertedPrices(@RequestParam(required = false) country: String?, @RequestParam productCode: Long) =
        priceService.getProductCurrentRate(country, productCode)

}