package com.bankingdemo.loans.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bankingdemo.loans.dto.ErrorResponseDto;
import com.bankingdemo.loans.dto.LoansDto;
import com.bankingdemo.loans.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(
		name = "CRUD REST APIs for Loans",
		description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RequestMapping(path = "/api", produces = {
		MediaType.APPLICATION_JSON_VALUE
})
@Validated
public interface ILoansController {

	@Operation(
			summary = "Create Loan REST API", description = "REST API to create new loan inside EazyBank"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201", description = "HTTP Status CREATED"
			), @ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	}
	)
	@PostMapping("/create")
	public ResponseEntity<Void> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
			summary = "Fetch Loan Details REST API",
			description = "REST API to fetch loan details based on a mobile number"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200", description = "HTTP Status OK"
			), @ApiResponse(
					responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	}
	)
	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("demobank-correlation-id") String correlationId, @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);

	@Operation(
	            summary = "Update Loan Details REST API",
	            description = "REST API to update loan details based on a loan number"
	    )
	    @ApiResponses({
	            @ApiResponse(
	                    responseCode = "200",
	                    description = "HTTP Status OK"
	            ),
	            @ApiResponse(
	                    responseCode = "417",
	                    description = "Expectation Failed"
	            ),
	            @ApiResponse(
	                    responseCode = "500",
	                    description = "HTTP Status Internal Server Error",
	                    content = @Content(
	                            schema = @Schema(implementation = ErrorResponseDto.class)
	                    )
	            )
	        }
	    )
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto);

	@Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber);
}
