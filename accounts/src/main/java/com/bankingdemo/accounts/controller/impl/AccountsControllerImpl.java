package com.bankingdemo.accounts.controller.impl;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingdemo.accounts.constants.AccountsConstants;
import com.bankingdemo.accounts.controller.IAccountsController;
import com.bankingdemo.accounts.dto.AccountsContactInfoDto;
import com.bankingdemo.accounts.dto.CustomerDto;
import com.bankingdemo.accounts.dto.ResponseDto;
import com.bankingdemo.accounts.service.IAccountsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@Slf4j
public class AccountsControllerImpl implements IAccountsController {

	private final IAccountsService accountsService;

	@Value("${build.version}")
	private String buildVersion;

	private Environment environment;
	private AccountsContactInfoDto accountsContactInfoDto;

	public AccountsControllerImpl(IAccountsService accountsService, Environment environment,
			AccountsContactInfoDto accountsContactInfoDto) {
		this.accountsService = accountsService;
		this.environment = environment;
		this.accountsContactInfoDto = accountsContactInfoDto;
	}

	@Override
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		accountsService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}

	@Override
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@Override
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = accountsService.updateAccount(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}
	}

	@Override
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = accountsService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}

	@Override
	@Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
	public ResponseEntity<String> getBuildInfo() {
		log.debug("getBuildInfo() method Invoked");
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	//accept same number of parameters as getBuildInfo, last one is Throwable
	public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
		log.debug("getBuildInfoFallback() method Invoked");
		return ResponseEntity.status(HttpStatus.OK).body("0.9");
	}


	@RateLimiter(name = "getJavaVersion" ,fallbackMethod = "getJavaVersionFallback")
	@Override
	public ResponseEntity<String> getJavaVersion() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME", String.class));
	}

	public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
		return ResponseEntity.status(HttpStatus.OK).body("Java 21");
	}

	@Override
	public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
	}

}
