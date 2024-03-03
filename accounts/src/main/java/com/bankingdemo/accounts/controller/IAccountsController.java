package com.bankingdemo.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankingdemo.accounts.dto.AccountsContactInfoDto;
import com.bankingdemo.accounts.dto.CustomerDto;
import com.bankingdemo.accounts.dto.ErrorResponseDto;
import com.bankingdemo.accounts.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Validated
@Tag(
		name = "CRUD REST APIs for accounts in Dummy Bank",
		description = "CRUD REST APIs for accounts in Dummy Bank to CREATE, UPDATE, FETCH, and DELETE accounts and costumers and details")
public interface IAccountsController {

	@Operation(
			summary = "Create Account REST API",
			description = "REST API to create new Customer & Account inside the Bank"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201", description = "HTTP Status CREATED"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto);

	@Operation(
			summary = "Fetch Account Details REST API",
			description = "REST API to fetch Customer &  Account details based on a mobile number")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(
			regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
			summary = "Update Account Details REST API",
			description = "REST API to update Customer &  Account details based on a account number")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto);

	@Operation(
			summary = "Delete Account & Customer Details REST API",
			description = "REST API to delete Customer &  Account details based on a mobile number")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
			summary = "Get build information",
			description = "Get build information that is deployed into this microservice")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/build-info")
	ResponseEntity<String> getBuildInfo();

	@Operation(
			summary = "Get java version information",
			description = "Get java version information that is deployed into this microservice")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion();

	@Operation(
			summary = "Get contact information",
			description = "Get contact information details that can be reached in case of Account microservice problems")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo();
}
