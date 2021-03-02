package resources.dynamo

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest

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
                        "id" to "db5b5fa0-742c-4d2f-a85f-80af929b086f",
                        "code" to "INR",
                        "country" to "Índia"
                    )
                )
            )
            countriesTable.putItem(
                Item.fromMap(
                    mapOf(
                        "id" to "b7e7d4fc-dbf5-4d75-b7ae-eeeff1d8df2d",
                        "code" to "USD",
                        "country" to "EUA"
                    )
                )
            )
        }
    }
}