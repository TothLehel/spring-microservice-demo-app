package com.bankingdemo.message.dto;

public record AccountsMessageDto(Long accountNumber, String name, String email, String mobileNumber) {
}
