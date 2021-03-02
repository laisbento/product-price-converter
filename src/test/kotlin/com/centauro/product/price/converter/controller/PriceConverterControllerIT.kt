package com.centauro.product.price.converter.controller

import com.centauro.product.price.converter.configuration.DynamoTestingConfig
import com.centauro.product.price.converter.configuration.IntegrationSetup
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@ContextConfiguration(classes = [DynamoTestingConfig::class])
class PriceConverterControllerIT : IntegrationSetup() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return product prices converted on all available currencies`() {
        mockMvc.get("/v1/price/convert?productCode=55308") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isAccepted }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.[0]price") { isNotEmpty }
            jsonPath("$.[1]price") { isNotEmpty }
            jsonPath("$.length()") { value(2) }
        }
    }

    @Test
    fun `should return product price converted on an expected country currency`() {
        mockMvc.get("/v1/price/convert?productCode=55308&countryId=b7e7d4fc-dbf5-4d75-b7ae-eeeff1d8df2d") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isAccepted }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.[0]country") { value("EUA") }
            jsonPath("$.[0]price") { isNotEmpty }
            jsonPath("$.length()") { value(1) }
        }
    }

    @Test
    fun `should return not found when product code was not found`() {
        mockMvc.get("/v1/price/convert?productCode=${Long.MAX_VALUE}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound }
            jsonPath("$.message") { value("Product ${Long.MAX_VALUE} was not found") }
        }
    }

    @Test
    fun `should return bad request when currency is not available for conversion`() {
        val countryId = UUID.randomUUID()
        mockMvc.get("/v1/price/convert?productCode=55308&countryId=${countryId}") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
            jsonPath("$.message") { value("Currency from country with id $countryId is not available") }
        }
    }
}