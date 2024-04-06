package com.bankingdemo.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import com.bankingdemo.accounts.dto.AccountsMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
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
@Slf4j
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
        }
        customer.setAccounts(createNewAccount(customer));
        customer = customerRepository.save(customer);
        sendCommunication(customer.getAccounts(), customer);
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

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered? {}", result);
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

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber != null){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );

            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }

        return isUpdated;
    }

}
