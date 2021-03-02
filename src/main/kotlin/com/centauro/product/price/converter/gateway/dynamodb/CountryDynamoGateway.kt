package com.centauro.product.price.converter.gateway.dynamodb

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.centauro.product.currency.calculator.exception.CurrencyNotAvailableException
import com.centauro.product.currency.calculator.model.entity.Country
import org.springframework.stereotype.Component
import java.util.*

@Component
class CountryDynamoGateway(
    private val dynamoDB: DynamoDB
) {

    fun findByName(name: String): String {
        return dynamoDB.getTable("countries")
            .scan(
                "country = :name",
                "code",
                null,
                mapOf(":name" to name)
            ).map { it.getString("code") }.firstOrNull()
            ?: throw CurrencyNotAvailableException("Currency from $name is not available")
    }

    fun findAll() = dynamoDB.getTable("countries").scan()
        .map {
            Country(
                UUID.fromString(it.getString("id")),
                it.getString("code"),
                it.getString("country")
            )
        }


}