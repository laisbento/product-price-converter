package com.centauro.product.currency.calculator.configuration

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class DynamoTestingConfig {

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.SA_EAST_1.name)
            ).build()
}