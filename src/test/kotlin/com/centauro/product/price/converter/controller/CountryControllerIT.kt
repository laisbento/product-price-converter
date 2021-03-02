package com.centauro.product.price.converter.controller

import com.centauro.product.price.converter.configuration.DynamoTestingConfig
import com.centauro.product.price.converter.configuration.IntegrationSetup
import com.centauro.product.price.converter.model.request.CountryRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*

@ContextConfiguration(classes = [DynamoTestingConfig::class])
class CountryControllerIT : IntegrationSetup() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return all available countries in the platform`() {
        mockMvc.get("/v1/country") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.[0]code") { value("USD") }
            jsonPath("$.[1]code") { value("INR") }
            jsonPath("$.length()") { value(2) }
        }
    }

    @Test
    fun `should create a new country`() {
        val countryRequest = CountryRequest(currencyCode = "AUD", countryName = "Austrália")
        mockMvc.post("/v1/country") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(countryRequest)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.currencyCode") { value("AUD") }
            jsonPath("$.countryName") { value("Austrália") }
        }
    }

    @Test
    fun `should delete a country`() {
        val countryId = "b7e7d4fc-dbf5-4d75-b7ae-eeeff1d8df2d"
        mockMvc.delete("/v1/country/${countryId}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent }
        }
    }

    @Test
    fun `should return bad request when trying to delete a country with an invalid id`() {
        mockMvc.delete("/v1/country/${UUID.randomUUID()}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
        }
    }
}