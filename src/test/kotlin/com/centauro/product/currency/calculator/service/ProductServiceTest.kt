package com.centauro.product.currency.calculator.service

import com.centauro.product.currency.calculator.exception.EntityNotFoundException
import com.centauro.product.currency.calculator.gateway.dynamodb.ProductDynamoGateway
import com.centauro.product.currency.calculator.model.entity.Product
import com.centauro.product.currency.calculator.response.mock.ProductResponseMock.Companion.getProduct
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @InjectMockKs
    private lateinit var productService: ProductService

    @MockK
    private lateinit var productDynamoGateway: ProductDynamoGateway

    @Test
    fun `should return product when code is valid`() {
        val product = getProduct()
        every { productDynamoGateway.findByProductCode(any()) } returns product

        val productResult = productService.getProduct(55308L)

        assertEquals(
            Product(product.getLong("code"), product.getNumber("price")), productResult
        )
        verify(exactly = 1) { productDynamoGateway.findByProductCode(any()) }
    }

    @Test
    fun `should throw exception when product code is invalid`() {
        every { productDynamoGateway.findByProductCode(any()) } returns null

        val entityNotFoundException = assertThrows<EntityNotFoundException> {
            productService.getProduct(Long.MAX_VALUE)
        }
        assertEquals("Product ${Long.MAX_VALUE} was not found", entityNotFoundException.message)
        verify(exactly = 1) { productDynamoGateway.findByProductCode(any()) }
    }

}