package resources.dynamo

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import java.util.*

class Data {
    companion object {
        fun createProductsData(dynamoDB: DynamoDB, createTableRequest: CreateTableRequest) {
            val productTable = dynamoDB.createTable(createTableRequest)
            productTable.putItem(
                Item.fromMap(
                    mapOf(
                        "code" to "55308",
                        "price" to 129.99
                    )
                )
            )
        }

        fun createCountriesData(dynamoDB: DynamoDB, createTableRequest: CreateTableRequest) {
            val countriesTable = dynamoDB.createTable(createTableRequest)
            countriesTable.putItem(
                Item.fromMap(
                    mapOf(
                        "id" to UUID.randomUUID().toString(),
                        "code" to "INR",
                        "country" to "Índia"
                    )
                )
            )
            countriesTable.putItem(
                Item.fromMap(
                    mapOf(
                        "id" to UUID.randomUUID().toString(),
                        "code" to "USD",
                        "country" to "EUA"
                    )
                )
            )
        }
    }
}