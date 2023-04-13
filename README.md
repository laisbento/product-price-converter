# Product Price Converter

### *ATTENTION: This application is not running on AWS anymore, so it's expected that it won't work properly.*

This application is responsible for converting the price of a product to the current currency of a country served by a retail platform.

## Specifications

-   *Manages the currencies of the countries served by the platform.* 
    - Allows inserting a new currency (country) for conversion calculation. The platform authorizes the removal of some currency if it no longer serves some country. For increased performance and decreased response time, all currencies served are cached in Redis with an expiration period of 7 days due to the low possibility of these value changes. If there is an insertion or deletion, the cache is invalidated, so it will update as soon as there is a request.
- *Performs the calculation of the value of a product based on a selected currency*
  - An endpoint that expects a valid product code and a currency id has been provided. The currency id is an optional attribute.
    - **Currency id not provided**: the product's value will be retrieved from the `product` table in DynamoDB. This entity is cached in Redis with the `productByCode` key and an expiration period of 2 days. Then, all currencies registered on the platform are retrieved from the `countries` table, also in DynamoDB. This list is cached in Redis with the `allCountries` key. For each currency, its value against the Brazilian real **from the previous day** is retrieved from the `free-api-currency` API, and with this value, the product value is calculated. The currency value against the Brazilian real is cached in Redis with the `currency-rate` key and an expiration period of 1 day so that its rate can be updated on the next day.
    - **Currency id provided**: the product's value will be retrieved from the `product` table in DynamoDB. This entity is cached in Redis with the `productByCode` key and an expiration period of 2 days. Then, the currency with the id sent in the request is retrieved from the `countries` table, also in DynamoDB. This currency is cached in Redis using its id as the key. With the currency code for this country, its value against the Brazilian real **from the previous day** is retrieved from the `free-api-currency` API. With this value, the product value is calculated. The currency value against the Brazilian real is cached in Redis with the `currency-rate` key and an expiration period of 1 day so that its rate can be updated the next day.

## Background

-   [AWS Redis](https://aws.amazon.com/pt/redis/) manages the application cache;
-   [AWS DynamoDB](https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/Introduction.html) stores the currencies/countries served by the retail platform and the product values;
-   [AWS ECS](https://docs.aws.amazon.com/pt_br/AmazonECS/latest/developerguide/Welcome.html) manages the application container;
- [Sonarcloud](https://sonarcloud.io/documentation) manages code quality;
-  [Currency Converter API](https://www.currencyconverterapi.com/)  returns currency rates;
- [New Relic](https://docs.newrelic.com/docs/using-new-relic/welcome-new-relic/get-started/introduction-new-relic) manages application performance.

## Architecture

The architecture of this application is built as follows:

![enter image description here](https://i.imgur.com/pu0HdYu.png)

## Requirements

-   Docker
-   Gradle 6.3+
-   Kotlin 1.4+

## How to run the application locally

Run in a terminal `cd docker && docker-compose up`, then run the application with an IDE or run the command: `./gradlew boot`

## Swagger
You can consult the application API and perform tests with its Swagger: http://localhost:8080/swagger-ui.html#

## Sonarcloud
You can monitor the code quality through the dashboard on Sonarcloud:
https://sonarcloud.io/dashboard?id=laisbento_product-price-converter

## New Relic
Currently, viewing the application's performance outside of New Relic is impossible. Those interested in viewing real-time data should request access from the creator of this repository.

*Application status on 02/03/2021:*
![enter image description here](https://i.imgur.com/CK5IK8D.png)
