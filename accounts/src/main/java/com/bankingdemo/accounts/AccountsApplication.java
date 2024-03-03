package com.bankingdemo.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bankingdemo.accounts.dto.AccountsContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(
		auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(value = {
		AccountsContactInfoDto.class
})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API documentation",
				description = "This API is made as a test project for learning microservices. It handles a dummy bank accounts data",
				version = "v1",
				license = @License(
						name = "Apache 2.0",
						url = "https://www.readmoreabout.com"),
				contact = @Contact(
						name = "John Doe",
						email = "john.doe@example.com",
						url = "https://www.linkedin.com")),
		externalDocs = @ExternalDocumentation(
				description = "This API is made as a test project for learning microservices. It handles a dummy bank accounts data",
				url = "https://www.readmoreabout.com"))
@EnableFeignClients //enables connections to other microservices
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
