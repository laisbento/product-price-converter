import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.sonarqube") version "3.1.1"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    jacoco
}

group = "com.centauro"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

jacoco {
    toolVersion = "0.8.5"
    reportsDir = file("$buildDir/reports/jacoco")
}

sonarqube {
    properties {
        property("sonar.exclusions", "**/configuration/**/*, **/gateway/dynamodb/**/*")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.projectKey", "laisbento_product-price-converter")
        property("sonar.organization", "laisbento")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

extra["springCloudVersion"] = "Hoxton.SR4"

repositories {
    mavenCentral()
}

var feignVersion = "2.2.7.RELEASE"
val newRelicVersion = "5.10.0"
val testContainersVersion = "1.15.1"
val mockKVersion = "1.9.3"
val junitJupiterVersion = "5.4.2"
val awsDynamoVersion = "1.11.96"
val swaggerVersion = "2.9.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.amazonaws:aws-java-sdk-dynamodb:$awsDynamoVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$feignVersion")
    implementation("com.newrelic.agent.java:newrelic-agent:$newRelicVersion")
    implementation("com.newrelic.agent.java:newrelic-api:$newRelicVersion")
    implementation("io.springfox:springfox-swagger2:$swaggerVersion")
    implementation("io.springfox:springfox-swagger-ui:$swaggerVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:$mockKVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    environment(
        "CURRENCY_API_KEY" to System.getenv("CURRENCY_API_KEY")
    )
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = false
    }
}
