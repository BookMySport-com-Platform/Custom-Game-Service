package com.bookmysport.custom_game_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CustomGameServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomGameServiceApplication.class, args);
	}

	@Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }

}
