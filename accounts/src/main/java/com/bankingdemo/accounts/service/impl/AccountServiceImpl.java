package com.bankingdemo.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bankingdemo.accounts.constants.AccountsConstants;
import com.bankingdemo.accounts.dto.AccountsDto;
import com.bankingdemo.accounts.dto.CustomerDto;
import com.bankingdemo.accounts.entity.Accounts;
import com.bankingdemo.accounts.entity.Customer;
import com.bankingdemo.accounts.exception.CustomerAlreadyExistsException;
import com.bankingdemo.accounts.exception.ResourceNotFoundException;
import com.bankingdemo.accounts.mapper.AccountsMapper;
import com.bankingdemo.accounts.mapper.CustomerMapper;
import com.bankingdemo.accounts.repository.AccountsRepository;
import com.bankingdemo.accounts.repository.CustomerRepository;
import com.bankingdemo.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //to Autowire in constructor
public class AccountServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	
	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
		}
		customer.setAccounts(createNewAccount(customer));
		customer = customerRepository.save(customer);
	}
	
	 /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
		newAccount.setCustomer(customer);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = findCustomerByMobileNumber(mobileNumber);
		if (customer.getAccounts() == null)
			throw new ResourceNotFoundException(Accounts.class.getCanonicalName(), "customerId",
					customer.getCustomerId().toString());

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(customer.getAccounts(), new AccountsDto()));
		return customerDto;
	}

	private Customer findCustomerByMobileNumber(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException(Customer.class.getCanonicalName(), "mobileNumber", mobileNumber));
		return customer;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if (accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException(Accounts.class.getCanonicalName(), "AccountNumber",
							accountsDto.getAccountNumber().toString()));
			AccountsMapper.mapToAccounts(accountsDto, accounts);
			accounts = accountsRepository.save(accounts);

			Customer customer = accounts.getCustomer();
			if (customer == null)
				throw new ResourceNotFoundException("Customer", "CustomerID", accounts.getCustomerId().toString());
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = findCustomerByMobileNumber(mobileNumber);
		customerRepository.delete(customer);
		return true;
	}

}
