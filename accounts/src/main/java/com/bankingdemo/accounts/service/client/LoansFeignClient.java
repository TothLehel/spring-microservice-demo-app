package com.bankingdemo.accounts.service.client;

import com.bankingdemo.accounts.dto.CardsDto;
import com.bankingdemo.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class) //registered name in eureka server
public interface LoansFeignClient {

    // this method signature has to match with
    // the exact signature of the endpoint declared in Cards service
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("demobank-correlation-id") String correlationId, @RequestParam String mobileNumber);


    }
