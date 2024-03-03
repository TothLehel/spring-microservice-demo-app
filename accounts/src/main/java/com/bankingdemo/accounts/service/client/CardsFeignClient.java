package com.bankingdemo.accounts.service.client;

import com.bankingdemo.accounts.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class) //registered name in eureka server
public interface CardsFeignClient {

    // this method signature has to match with
    // the exact signature of the endpoint declared in Cards service
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("demobank-correlation-id") String correlationId, @RequestParam String mobileNumber);


    }
