package com.centauro.product.currency.calculator.service

import com.centauro.product.currency.calculator.exception.EntityNotFoundException
import com.centauro.product.currency.calculator.gateway.dynamodb.ProductDynamoGateway
import com.centauro.product.currency.calculator.model.entity.Product
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productDynamoGateway: ProductDynamoGateway
) {
    @Cacheable(
        value = ["productByCode"],
        key = "{#code}",
        cacheManager = "productCacheManager"
    )
    fun getProduct(code: Long): Product {
        val product = productDynamoGateway.findByProductCode(code)
            ?: throw EntityNotFoundException("Product $code was not found")
        return jacksonObjectMapper().readValue(product.toJSON())
    }
}