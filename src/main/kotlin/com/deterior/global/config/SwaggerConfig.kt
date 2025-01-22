package com.deterior.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
            .components(Components())
            .info(apiInfo())

    private fun apiInfo(): Info = Info()
            .title("Desplay API document")
            .description("Desplay API 사용법에 대한 문서")
            .version("1.0.0")

    @Bean
    fun hiddenApiInfo(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("hidden")
        .pathsToExclude("/test/**")
        .build()
}