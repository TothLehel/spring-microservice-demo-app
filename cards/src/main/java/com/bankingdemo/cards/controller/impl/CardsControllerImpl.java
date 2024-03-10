package com.bankingdemo.cards.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingdemo.cards.constants.CardsConstants;
import com.bankingdemo.cards.controller.ICardsController;
import com.bankingdemo.cards.dto.CardsDto;
import com.bankingdemo.cards.dto.ResponseDto;
import com.bankingdemo.cards.service.ICardsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@Slf4j
public class CardsControllerImpl implements ICardsController {

    private ICardsService cardsService;

    @Override
    public ResponseEntity<Void> createCard(@Valid @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits") String mobileNumber) {
        cardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("demobank-correlation-id") String correlationId,
                                                     @RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        log.debug("fetchCardDetails method start");
        CardsDto fetchCard = cardsService.fetchCard(mobileNumber);
        log.debug("fetchCardDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(fetchCard);
    }

    @Override
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid CardsDto cardsDto) {
        boolean updateCard = cardsService.updateCard(cardsDto);
        if (updateCard) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }

    }

    @Override
    public ResponseEntity<ResponseDto> deleteCardDetails(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean deleteCard = cardsService.deleteCard(mobileNumber);
        if (deleteCard) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }


}
