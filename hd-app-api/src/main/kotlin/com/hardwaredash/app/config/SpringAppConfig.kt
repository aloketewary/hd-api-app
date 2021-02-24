package com.hardwaredash.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class SpringAppConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowedOrigins("http://localhost:4200",
                "https://hardware-dash-app.herokuapp.com")
    }

    @Bean(name = ["multipartResolver"])
    fun multipartResolver(): CommonsMultipartResolver = CommonsMultipartResolver()
}