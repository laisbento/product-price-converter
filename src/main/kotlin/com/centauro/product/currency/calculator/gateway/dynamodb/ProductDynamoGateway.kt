package com.centauro.product.currency.calculator.gateway.dynamodb

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.KeyAttribute
import com.centauro.product.currency.calculator.exception.ExternalServiceException
import org.springframework.stereotype.Component

@Component
class ProductDynamoGateway(
    private val dynamoDB: DynamoDB
) {
    fun findByProductCode(code: Long): Item? {
        try {
            return dynamoDB.getTable("product")
                .getItem(KeyAttribute("code", code.toString()))
        } catch (ex: Exception) {
            throw ExternalServiceException("Unable to get product at this time. Error: ", ex)
        }
    }
}