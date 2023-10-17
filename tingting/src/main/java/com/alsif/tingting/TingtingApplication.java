package com.alsif.tingting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TingtingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TingtingApplication.class, args);
	}

}
