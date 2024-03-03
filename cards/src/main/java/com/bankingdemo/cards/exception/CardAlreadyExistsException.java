package com.bankingdemo.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CardAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CardAlreadyExistsException(String message, Object items) {
		super(String.format(message, items));
	}

}
