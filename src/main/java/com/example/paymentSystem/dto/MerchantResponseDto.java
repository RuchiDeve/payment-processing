package com.example.paymentSystem.dto;

import lombok.Data;

@Data
public class MerchantResponseDto {
    private Long id;
    private String companyName;
    private String address;
    private String businessType;
    private String email;
    private String phoneNumber;
    private String bankAccountDetails;
    private String token;

}