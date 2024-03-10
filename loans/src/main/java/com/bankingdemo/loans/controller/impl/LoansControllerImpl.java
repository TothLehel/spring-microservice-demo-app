package com.bankingdemo.loans.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bankingdemo.loans.constants.LoansConstants;
import com.bankingdemo.loans.controller.ILoansController;
import com.bankingdemo.loans.dto.LoansDto;
import com.bankingdemo.loans.dto.ResponseDto;
import com.bankingdemo.loans.service.ILoansService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Slf4j
public class LoansControllerImpl implements ILoansController {

	private ILoansService loansService;

	@Override
	public ResponseEntity<Void> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber) {
		loansService.createLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

	@Override
	public ResponseEntity<LoansDto> fetchLoanDetails(
			@RequestHeader("demobank-correlation-id") String correlationId,
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
					message = "Mobile number must be 10 digits") String mobileNumber) {
		log.debug("fetchLoanDetails method start");
		LoansDto fetchLoan = loansService.fetchLoan(mobileNumber);
		log.debug("fetchLoanDetails method end");
		return ResponseEntity.status(HttpStatus.OK).body(fetchLoan);
    }

	@Override
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
		boolean isUpdated = loansService.updateLoan(loansDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

	@Override
	public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
			message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = loansService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }

}
