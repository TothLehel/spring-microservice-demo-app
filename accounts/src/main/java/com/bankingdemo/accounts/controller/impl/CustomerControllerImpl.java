package com.bankingdemo.accounts.controller.impl;

import com.bankingdemo.accounts.controller.ICustomerController;
import com.bankingdemo.accounts.dto.AccountsContactInfoDto;
import com.bankingdemo.accounts.dto.CustomerDetailsDto;
import com.bankingdemo.accounts.service.ICustomerService;
import com.bankingdemo.accounts.service.impl.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;

    public CustomerControllerImpl(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(String correlationId, String mobileNumber) {
        log.debug("demobank-correlation-id found {}" , correlationId);
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
