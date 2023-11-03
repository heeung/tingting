package com.alsif.tingting.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173")
			.allowedOrigins("http://localhost:5174")
			.allowedOrigins("http://k9d209.p.ssafy.io:9000")
			.allowedOrigins("https://k9d209.p.ssafy.io")
			.allowedHeaders("*")
			.allowedMethods("*");
	}
}
