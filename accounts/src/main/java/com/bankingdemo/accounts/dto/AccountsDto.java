package com.bankingdemo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account information")
public class AccountsDto {

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Account number number must be 10 digits")
	@Schema(description = "Name of the customer", example = "1234567891")
	private Long accountNumber;

	@Schema(description = "Account type of Bank account", example = "Savings")
	@NotEmpty(message = "AccountType can not be empty")
	private String accountType;

	@Schema(description = "Bank branch adress", example = "615 Nashville ")
	@NotEmpty(message = "BranchAddress can not be empty")
	private String branchAddress;
}
