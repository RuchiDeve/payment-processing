package com.example.paymentSystem.service;


import com.example.paymentSystem.dto.MerchantRequestDto;
import com.example.paymentSystem.dto.MerchantResponseDto;
import com.example.paymentSystem.model.Merchant;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MerchantService {
    MerchantResponseDto createMerchant(MerchantRequestDto merchantDto);

    Optional<Merchant> findByEmail(String email);

    MerchantResponseDto updateMerchant(Long id, MerchantRequestDto updatedMerchantDto);

}
