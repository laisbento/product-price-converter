package com.centauro.product.currency.calculator.response.mock

import com.amazonaws.services.dynamodbv2.document.Item

class ProductResponseMock {

    companion object {
        internal fun getProduct() = Item.fromMap(
            mapOf(
                "code" to "55308",
                "price" to 129.99
            )
        )
    }
}