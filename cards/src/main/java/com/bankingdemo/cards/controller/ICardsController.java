package com.bankingdemo.cards.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bankingdemo.cards.dto.CardsDto;
import com.bankingdemo.cards.dto.ErrorResponseDto;
import com.bankingdemo.cards.dto.ResponseDto;

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
		name = "CRUD REST APIs for Cards in Dummy Bank",
		description = "CRUD REST APIs for cards in Dummy Bank to CREATE, UPDATE, FETCH, and DELETE cards data for costumers")
public interface ICardsController {

	@Operation(
			summary = "Create Cards REST API", description = "REST API to create new Card for given Customer"
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
	public ResponseEntity<Void> createCard(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
			summary = "Fetch Card Details REST API", description = "REST API to fetch Customer's card details")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(
									implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/fetch")
	public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("demobank-correlation-id") String correlationId, @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
			summary = "Update Card Details REST API", description = "REST API to update Customer's card details")
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
	public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto);

	@Operation(
			summary = "Delete Card from Account REST API",
			description = "REST API to delete Customer's card from the system")
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
	public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);
}
