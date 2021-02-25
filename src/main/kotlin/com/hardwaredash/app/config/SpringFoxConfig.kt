package com.hardwaredash.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.*
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.SecurityConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
@Import(
    BeanValidatorPluginsConfiguration::class
)
class SpringFoxConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .globalResponses(
                HttpMethod.GET,
                mutableListOf(
                    ResponseBuilder()
                        .code("500")
                        .description("500 message")
                        .build(),
                    ResponseBuilder()
                        .code("403")
                        .description("Forbidden!")
                        .build()
                )
            )
            .apiInfo(getApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.hardwaredash.app.controller"))
            .paths(PathSelectors.any())
            .build()
//            .securitySchemes(listOf(securityScheme()))
    }

//    @Bean
//    fun security(): SecurityConfiguration {
//        return SecurityConfigurationBuilder.builder()
//            .clientId(CLIENT_ID)
//            .clientSecret(CLIENT_SECRET)
//            .scopeSeparator(" ")
//            .useBasicAuthenticationWithAccessCodeGrant(true)
//            .build()
//    }

    private fun getApiInfo(): ApiInfo {
        val contact = Contact("Aloke Tewary", "http://aloketewary.app", "aloketewary@gmail.com")
        return ApiInfoBuilder()
            .title("HardwareDash Rest Api")
            .description("HardwareDash Api Definition")
            .version("1.0.0")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .contact(contact)
            .build()
    }

//    private fun securityScheme(): SecurityScheme {
//        val grantType: GrantType = AuthorizationCodeGrantBuilder()
//            .tokenEndpoint(TokenEndpoint(AUTH_SERVER.toString() + "/token", "oauthtoken"))
//            .tokenRequestEndpoint(
//                TokenRequestEndpoint(AUTH_SERVER.toString() + "/authorize", CLIENT_ID, CLIENT_SECRET)
//            )
//            .build()
//        return OAuthBuilder().name("spring_oauth")
//            .grantTypes(listOf(grantType))
//            .scopes(listOf(scopes()))
//            .build()
//    }
}