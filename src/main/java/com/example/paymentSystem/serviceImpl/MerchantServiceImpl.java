package com.example.paymentSystem.serviceImpl;
import com.example.paymentSystem.dto.MerchantRequestDto;
import com.example.paymentSystem.dto.MerchantResponseDto;
import com.example.paymentSystem.model.Merchant;
import com.example.paymentSystem.repository.MerchantRepository;
import com.example.paymentSystem.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;

    public MerchantServiceImpl(MerchantRepository merchantRepository, PasswordEncoder passwordEncoder) {
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public MerchantResponseDto createMerchant(MerchantRequestDto merchantDto) {
        Merchant merchant = new Merchant();
        merchant.setCompanyName(merchantDto.getCompanyName());
        merchant.setAddress(merchantDto.getAddress());
        merchant.setBusinessType(merchantDto.getBusinessType());
        merchant.setEmail(merchantDto.getEmail());
        merchant.setPhoneNumber(merchantDto.getPhoneNumber());
        merchant.setBankAccountDetails(merchantDto.getBankAccountDetails());
        merchant.setPassword(passwordEncoder.encode(merchantDto.getPassword()));
        Merchant savedMerchant = merchantRepository.save(merchant);
        return convertToDto(savedMerchant);
    }

    @Override
    public Optional<Merchant> findByEmail(String email) {
        return merchantRepository.findByEmail(email);
    }


    @Override
    public MerchantResponseDto updateMerchant(Long id, MerchantRequestDto updatedMerchantDto) {
        Merchant updatedMerchant = merchantRepository.findById(id)
                .map(existingMerchant -> {
                    existingMerchant.setCompanyName(updatedMerchantDto.getCompanyName());
                    existingMerchant.setAddress(updatedMerchantDto.getAddress());
                    existingMerchant.setBusinessType(updatedMerchantDto.getBusinessType());
                    existingMerchant.setEmail(updatedMerchantDto.getEmail());
                    existingMerchant.setPhoneNumber(updatedMerchantDto.getPhoneNumber());
                    existingMerchant.setBankAccountDetails(updatedMerchantDto.getBankAccountDetails());
                    return merchantRepository.save(existingMerchant);
                })
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        return convertToDto(updatedMerchant);
    }private MerchantResponseDto convertToDto(Merchant merchant) {

        MerchantResponseDto dto = new MerchantResponseDto();
        dto.setId(merchant.getId());
        dto.setCompanyName(merchant.getCompanyName());
        dto.setAddress(merchant.getAddress());
        dto.setBusinessType(merchant.getBusinessType());
        dto.setEmail(merchant.getEmail());
        dto.setPhoneNumber(merchant.getPhoneNumber());
        dto.setBankAccountDetails(merchant.getBankAccountDetails());
        return dto;
    }

}
