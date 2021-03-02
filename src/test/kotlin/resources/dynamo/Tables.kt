package resources.dynamo

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType


class Tables {
    companion object {
        fun tableProduct(): CreateTableRequest {
            return CreateTableRequest()
                .withAttributeDefinitions(
                    AttributeDefinition("code", ScalarAttributeType.S),
                )
                .withKeySchema(KeySchemaElement("code", KeyType.HASH))
                .withProvisionedThroughput(ProvisionedThroughput(5L, 5L))
                .withTableName("product")
        }

        fun tableCountries(): CreateTableRequest = CreateTableRequest()
            .withAttributeDefinitions(
                AttributeDefinition("id", ScalarAttributeType.S),
            )
            .withKeySchema(KeySchemaElement("id", KeyType.HASH))
            .withProvisionedThroughput(ProvisionedThroughput(5L, 5L))
            .withTableName("countries")

        fun dropTableProduct(): DeleteTableRequest = DeleteTableRequest().withTableName("product")
        fun dropTableCountries(): DeleteTableRequest = DeleteTableRequest().withTableName("countries")
    }
}


