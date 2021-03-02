package com.centauro.product.currency.calculator.configuration

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import resources.dynamo.Data
import resources.dynamo.Tables
import java.io.File

@Testcontainers
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class IntegrationSetup {

    companion object {
        class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)

        private val composeContainer: KDockerComposeContainer by lazy {
            KDockerComposeContainer(File("docker/docker-compose.yml"))
                .withExposedService("redis_1", 6379)
                .withLocalCompose(true)
                .waitingFor(
                    "localstack_1",
                    Wait.forLogMessage(".*Ready.*", 1)
                )
        }

        init {
            composeContainer.start()
        }
    }

    @Autowired
    @Qualifier("dynamoDB")
    private lateinit var dynamoDB: DynamoDB

    @BeforeAll
    fun setUpBeforeClass() {
        Data.createCountriesData(dynamoDB, Tables.tableCountries())
        Data.createProductsData(dynamoDB, Tables.tableProduct())
    }

    @AfterAll
    fun setUpAfterClass() {
        Tables.dropTableCountries()
        Tables.dropTableProduct()
    }

}