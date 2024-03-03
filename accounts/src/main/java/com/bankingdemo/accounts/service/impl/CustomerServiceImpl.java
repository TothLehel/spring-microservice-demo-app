package com.bankingdemo.accounts.service.impl;

import com.bankingdemo.accounts.controller.ICustomerController;
import com.bankingdemo.accounts.dto.*;
import com.bankingdemo.accounts.entity.Accounts;
import com.bankingdemo.accounts.entity.Customer;
import com.bankingdemo.accounts.exception.ResourceNotFoundException;
import com.bankingdemo.accounts.mapper.AccountsMapper;
import com.bankingdemo.accounts.mapper.CustomerMapper;
import com.bankingdemo.accounts.repository.AccountsRepository;
import com.bankingdemo.accounts.repository.CustomerRepository;
import com.bankingdemo.accounts.service.ICustomerService;
import com.bankingdemo.accounts.service.client.CardsFeignClient;
import com.bankingdemo.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    private AccountServiceImpl accountService;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customerDto, new CustomerDetailsDto());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(cardsDtoResponseEntity != null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        ResponseEntity<LoansDto> loansDtoResponseEntity =  loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(loansDtoResponseEntity != null) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
