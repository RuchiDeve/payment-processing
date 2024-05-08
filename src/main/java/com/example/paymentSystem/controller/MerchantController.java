package com.example.paymentSystem.controller;


import com.example.paymentSystem.dto.MerchantRequestDto;
import com.example.paymentSystem.dto.MerchantResponseDto;
import com.example.paymentSystem.service.MerchantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @PostMapping("/signup")
    public ResponseEntity<MerchantResponseDto> signup(@RequestBody MerchantRequestDto merchantDto) {
        MerchantResponseDto createdMerchant = merchantService.createMerchant(merchantDto);
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MerchantResponseDto> login(@RequestBody MerchantRequestDto merchantDto) {
        MerchantResponseDto merchantResponseDto = merchantService.login(merchantDto);
        return new ResponseEntity<>(merchantResponseDto, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MerchantResponseDto> updateMerchant(@PathVariable Long id, @RequestBody MerchantRequestDto updatedMerchantDto) {
        MerchantResponseDto updatedMerchant = merchantService.updateMerchant(id, updatedMerchantDto);
        return ResponseEntity.ok(updatedMerchant);
    }
}
