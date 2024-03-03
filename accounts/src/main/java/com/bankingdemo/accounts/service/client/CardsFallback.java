package com.bankingdemo.accounts.service.client;

import com.bankingdemo.accounts.dto.CardsDto;
import com.bankingdemo.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{


    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null; //return this when failing
    }
}
