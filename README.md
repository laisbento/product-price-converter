# Product Price Converter

Essa aplicação é responsável por converter o preço de um produto para a moeda corrente de um país atendido por uma plataforma de varejo.

## Especificações

-   *Administra as moedas dos países atendidos pela plataforma.* 
    - Possibilita a inserção de uma nova moeda (país) para cálculo de conversão. Caso a moeda (país) não seja mais atendida pela plataforma, é possível delete-lá. Para um aumento de performance e diminuição de tempo de resposta, todas as moedas atendidas são cacheadas no Redis com um prazo de expiração de 7 dias, devido a baixa possibilidade de alteração de valores. Se houver uma inserção ou deleção, o cache é invalidado para que seja atualizado assim que houver alguma request.
- *Realiza o cálculo do valor de um produto baseado em uma moeda selecionada.*
  - Foi disponibilizada um endpoint que espera o código válido de um produto e o id de uma moeda.  O id da moeda é um atributo não obrigatório.
    - **Id da moeda não informado**: o valor do produto será buscado na tabela `product` no DynamoDB. Essa entidade é cacheada no Redis com a chave `productByCode` e com um prazo de expiração de 2 dias. Depois, é buscado na tabela `countries`, também no Dynamo DB, todas as moedas cadastradas na plataforma. Essa lista é cacheada no Redis com a chave `allCountries`. Para cada moeda, é buscado seu valor contra o real, **do dia anterior**, na API `free-api-currency` e com esse valor, é calculado o valor do produto. O valor da moeda contra o real é cacheado no Redis com a chave `currency-rate` e com o prazo de expiração de 1 dia, para que no próximo dia, seu rate seja atualizado.
    - **Id da moeda informado**: o valor do produto será buscado na tabela `product` no DynamoDB. Essa entidade é cacheada no Redis com a chave `productByCode` e com um prazo de expiração de 2 dias. Depois, é buscado na tabela `countries`, também no Dynamo DB, a moeda que possui o id enviado na request. Essa moeda é cacheada no Redis utilizando seu id como chave. Com o código da moeda desse país, é buscado seu valor contra o real, **do dia anterior**, na API `free-api-currency` e com esse valor, é calculado o valor do produto. O valor da moeda contra o real é cacheado no Redis com a chave `currency-rate` e com o prazo de expiração de 1 dia, para que no próximo dia, seu rate seja atualizado.

## Background

-   [AWS Redis](https://aws.amazon.com/pt/redis/)  administra o cache da aplicação;
-   [AWS DynamoDB](https://docs.aws.amazon.com/pt_br/amazondynamodb/latest/developerguide/Introduction.html) armazena as moedas/países atentidos pela plataforma de varejo e o valor dos produtos;
-   [AWS ECS](https://docs.aws.amazon.com/pt_br/AmazonECS/latest/developerguide/Welcome.html) administra o container da aplicação;
- [Sonarcloud](https://sonarcloud.io/documentation) administra a qualidade do código;
-  [Currency Converter API](https://www.currencyconverterapi.com/)  retorna os rates das moedas;
- [New Relic](https://docs.newrelic.com/docs/using-new-relic/welcome-new-relic/get-started/introduction-new-relic) administra o desempenho da aplicação.

## Arquitetura

A arquitetura dessa aplicação está constuída da seguinte maneira:

![enter image description here](https://i.imgur.com/pu0HdYu.png)

## Requerimentos

-   Docker
-   Gradle 6.3+
-   Kotlin 1.4+

## Como rodar a aplicação localmente

`cd docker && docker-compose up` então rode a aplicação com alguma IDE ou o comando:
`./gradlew bootRun`

## Swagger
É possível consultar a API da aplicação e realizar testes com seu Swagger:
http://ec2co-ecsel-6j4uyn34m8k3-1636673804.sa-east-1.elb.amazonaws.com:8080/swagger-ui.html#

## Sonarcloud
É possível acompanhar a qualidade do código através do dashboard no Sonarcloud:
https://sonarcloud.io/dashboard?id=laisbento_product-price-converter

## New Relic
Atualmente não é possível visualizar o desempenho da aplicação fora do New Relic. Os interessados em visualizar os dados em tempo real devem solicitar acesso a criadora desse repositório.
*Status da aplicação no dia 02/03/2021:*
![enter image description here](https://i.imgur.com/CK5IK8D.png)
