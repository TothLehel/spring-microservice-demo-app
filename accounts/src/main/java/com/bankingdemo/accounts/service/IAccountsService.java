package com.bankingdemo.accounts.service;

import com.bankingdemo.accounts.dto.CustomerDto;

public interface IAccountsService {
	
	/**
	 * 
	 * @param customerDto - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);

	/**
	 * 
	 * @param mobileNumber - mobile number for searching account
	 * @return
	 */
	CustomerDto fetchAccount(String mobileNumber);

	/**
	 * 
	 * @param customerDto
	 * @return boolean indicating if the update of Account details was a success
	 */
	boolean updateAccount(CustomerDto customerDto);
	
	/**
	 * 
	 * @param mobileNumber
	 * @return boolean indicating if the update of Account details was a success
	 */
	boolean deleteAccount(String mobileNumber);

	boolean updateCommunicationStatus(Long accountNumber);
}
