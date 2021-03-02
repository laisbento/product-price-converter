package com.centauro.product.price.converter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(
            ApiInfoBuilder()
                .title("Product Price Converter API")
                .build()
        )
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.centauro.product.price.converter.controller"))
        .build()
        .useDefaultResponseMessages(false)
        .enableUrlTemplating(false)
}
