package com.centauro.product.currency.calculator.configuration.aws

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoConfiguration {

    fun amazonAWSCredentialsProvider(): AWSCredentialsProvider = AWSStaticCredentialsProvider(EnvironmentVariableCredentialsProvider().credentials)

    @Bean
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB) = DynamoDBMapper(amazonDynamoDB)

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder.standard()
            .withCredentials(amazonAWSCredentialsProvider())
            .withRegion(Regions.SA_EAST_1).build()

    @Bean
    fun dynamoDB(client: AmazonDynamoDB) = DynamoDB(client)

}