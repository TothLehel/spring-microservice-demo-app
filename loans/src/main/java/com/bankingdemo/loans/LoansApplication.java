package com.bankingdemo.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API documentation",
				description = "This API is made as a test project for learning microservices. It handles a dummy bank Loans data",
				version = "v1",
				license = @License(
						name = "Apache 2.0",
						url = "https://www.readmoreabout.com"),
				contact = @Contact(
						name = "John Doe", email = "john.doe@example.com", url = "https://www.linkedin.com")),
		externalDocs = @ExternalDocumentation(
				description = "This API is made as a test project for learning microservices. It handles a dummy Loans card data",
				url = "https://www.readmoreabout.com"))
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}
}
