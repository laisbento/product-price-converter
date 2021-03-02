package com.centauro.product.price.converter.controller

import com.centauro.product.price.converter.model.request.CountryRequest
import com.centauro.product.price.converter.model.response.ResponseError
import com.centauro.product.price.converter.service.CountryService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Api(
    tags = ["Countries Currency Management"],
    description = "API responsible for managing all currencies available in the platform"
)
@RestController
@RequestMapping("/v1/country")
class CountryController(
    private val countryService: CountryService
) {

    @ApiOperation("Get all countries available in the platform at this moment")
    @ApiResponses(
        ApiResponse(
            code = 202,
            message = "All countries"
        ),
        ApiResponse(
            code = 503,
            message = "Failed to get all countries available from an external service",
            response = ResponseError::class
        )
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getCountries() = countryService.getAllCountries()


    @ApiOperation("Insert a new country to turn its currency available in the platform")
    @ApiResponses(
        ApiResponse(
            code = 201,
            message = "Country inserted"
        ),
        ApiResponse(
            code = 503,
            message = "Failed to create a country in an external service",
            response = ResponseError::class
        )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCountry(@RequestBody countryRequest: CountryRequest) = countryService.createCountry(countryRequest)

    @ApiOperation("Delete a country to turn its currency unavailable in the platform")
    @ApiResponses(
        ApiResponse(
            code = 204,
            message = "Country deleted"
        ),
        ApiResponse(
            code = 503,
            message = "Failed to delete a country in an external service",
            response = ResponseError::class
        )
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCountry(@PathVariable("id") countryId: UUID) = countryService.deleteCountry(countryId)
}