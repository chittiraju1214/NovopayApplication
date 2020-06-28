package com.example.novopay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableWebSecurity
@SpringBootApplication
public class NovopayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovopayApplication.class, args);
	}

}
