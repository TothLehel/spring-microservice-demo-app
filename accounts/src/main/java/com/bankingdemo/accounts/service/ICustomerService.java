package com.bankingdemo.accounts.service;

import com.bankingdemo.accounts.dto.CustomerDetailsDto;
import com.bankingdemo.accounts.dto.CustomerDto;

public interface ICustomerService {
	

	/**
	 * 
	 * @param mobileNumber - mobile number for searching account
	 * @return
	 */
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);


}
