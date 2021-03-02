package com.centauro.product.price.converter.gateway.dynamodb

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.centauro.product.price.converter.exception.CurrencyNotAvailableException
import com.centauro.product.price.converter.model.entity.Country
import com.centauro.product.price.converter.model.request.CountryRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class CountryDynamoGateway(
    private val dynamoDB: DynamoDB
) {

    fun findById(countryId: UUID): Country {
        val country = (dynamoDB.getTable("countries")
            .getItem("id", countryId.toString())
            ?: throw CurrencyNotAvailableException("Currency from country with id $countryId is not available"))
        return Country(
            id = UUID.fromString(country.getString("id")),
            code = country.getString("code"),
            name = country.getString("country")
        )
    }

    fun findAll() = dynamoDB.getTable("countries").scan()
        .map {
            Country(
                UUID.fromString(it.getString("id")),
                it.getString("code"),
                it.getString("country")
            )
        }

    fun createCountry(countryRequest: CountryRequest) {
        dynamoDB.getTable("countries")
            .putItem(
                Item.fromMap(
                    mapOf(
                        "id" to UUID.randomUUID().toString(),
                        "code" to countryRequest.currencyCode,
                        "country" to countryRequest.countryName
                    )
                )
            )
    }

    fun deleteCountry(countryId: UUID) {
        dynamoDB.getTable("countries")
            .deleteItem("id", countryId.toString())
    }

}