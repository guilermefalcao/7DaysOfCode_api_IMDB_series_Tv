package com.imdb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// Classe principal da aplicação Spring Boot
@SpringBootApplication
public class ImdbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbApiApplication.class, args);
	}

	// Bean do RestTemplate para realizar requisições HTTP
	// O Spring gerencia este objeto e o disponibiliza para injeção
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
