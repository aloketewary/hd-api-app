package com.hardwaredash.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.beans.ConstructorProperties


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
class AppConfig {
    lateinit var name: String
    lateinit var environment: String
    var enabled: Boolean = false
}

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app.login")
class LoginConfig {
    lateinit var jwtSecret: String
    lateinit var jwtExpirationMs: String
}

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app.mongodb")
class MongoConfiguration {
    lateinit var uri: String
    lateinit var authenticationDatabase: String
    lateinit var username: String
    lateinit var password: String
    lateinit var database: String
    lateinit var host: String
    lateinit var port: String
}