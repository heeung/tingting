package com.alsif.tingting.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
	info = @Info(
		title = "tingting ticketing-service API 명세서",
		description = "tingting ticketing-service API 명세서",
		version = "v2"
	)
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.components(new Components());
	}
}
