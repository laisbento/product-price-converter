package com.centauro.product.price.converter.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class RedisConfiguration : CachingConfigurerSupport() {

    @Bean
    fun redisConnectionFactory(
        @Value("\${spring.redis.host}") host: String,
        @Value("\${spring.redis.port}") port: Int
    ): LettuceConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(host, port)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    @Primary
    fun defaultCacheManager(
        @Value("\${cache.service.default.ttl-seconds}") ttlSeconds: Long,
        redisConnectionFactory: RedisConnectionFactory
    ): CacheManager {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(ttlSeconds))
        ) .build()
    }

    @Bean
    fun productCacheManager(
        @Value("\${cache.service.product.ttl-days}") ttlDays: Long,
        redisConnectionFactory: RedisConnectionFactory
    ): CacheManager {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(ttlDays))
        ).build()
    }

    @Bean
    fun currencyRateCacheManager(
        @Value("\${cache.service.currency-rate.ttl-days}") ttlDays: Long,
        redisConnectionFactory: RedisConnectionFactory
    ): CacheManager {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(ttlDays))
        ).build()
    }

    @Bean
    fun countryCacheManager(
        @Value("\${cache.service.country.ttl-days}") ttlDays: Long,
        redisConnectionFactory: RedisConnectionFactory
    ): CacheManager {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(ttlDays))
        ).build()
    }
}