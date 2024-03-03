package com.bankingdemo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response", description = "Schema to hold successful HTTP response information")
public class ResponseDto {

	@Schema(name = "HTTP status code", example = "200")
	private String statusCode;
	@Schema(name = "HTTP status message", example = "Request processed succesfully")
	private String statusMsg;
	
}
