package com.bankingdemo.accounts.mapper;

import com.bankingdemo.accounts.dto.CustomerDetailsDto;
import com.bankingdemo.accounts.dto.CustomerDto;
import com.bankingdemo.accounts.entity.Customer;

public class CustomerMapper {

	public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		customerDto.setMobileNumber(customer.getMobileNumber());
		return customerDto;
	}

	public static CustomerDetailsDto mapToCustomerDetailsDto(CustomerDto customer, CustomerDetailsDto customerDetailsDto) {
		customerDetailsDto.setName(customer.getName());
		customerDetailsDto.setEmail(customer.getEmail());
		customerDetailsDto.setMobileNumber(customer.getMobileNumber());
		customerDetailsDto.setAccountsDto(customer.getAccountsDto());
		return customerDetailsDto;
	}

	public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setMobileNumber(customerDto.getMobileNumber());
		return customer;
	}

}