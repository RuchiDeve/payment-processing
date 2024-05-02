package com.example.paymentSystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "merchants")

public class Merchant {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
public long id;
    private String companyName;
    private String address;
    private String businessType;
    private String email;
    private String phoneNumber;
    private String bankAccountDetails;
    private String password;
}
