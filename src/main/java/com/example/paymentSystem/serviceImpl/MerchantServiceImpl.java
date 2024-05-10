package com.example.paymentSystem.serviceImpl;

import com.example.paymentSystem.config.JwtService;
import com.example.paymentSystem.dto.MerchantRequestDto;
import com.example.paymentSystem.dto.MerchantResponseDto;
import com.example.paymentSystem.exception.InvalidCredentialsException;
import com.example.paymentSystem.model.Merchant;
import com.example.paymentSystem.repository.MerchantRepository;
import com.example.paymentSystem.service.MerchantService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
    public MerchantResponseDto login(MerchantRequestDto merchantDto) {
        Optional<Merchant> optionalMerchant = merchantRepository.findByEmail(merchantDto.getEmail());

        if (optionalMerchant.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }


        Merchant merchant = optionalMerchant.get();

        if (!passwordEncoder.matches(merchantDto.getPassword(), merchant.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(merchant);

        MerchantResponseDto responseDto = convertToDto(merchant);
        responseDto.setToken(token);

        return responseDto;
    }


    @Override
    public MerchantResponseDto updateMerchant(Long id, MerchantRequestDto updatedMerchantDto) {
        Merchant updatedMerchant = merchantRepository.findById(id).map(existingMerchant -> {
            existingMerchant.setCompanyName(updatedMerchantDto.getCompanyName());
            existingMerchant.setAddress(updatedMerchantDto.getAddress());
            existingMerchant.setBusinessType(updatedMerchantDto.getBusinessType());
            existingMerchant.setEmail(updatedMerchantDto.getEmail());
            existingMerchant.setPhoneNumber(updatedMerchantDto.getPhoneNumber());
            existingMerchant.setBankAccountDetails(updatedMerchantDto.getBankAccountDetails());
            return merchantRepository.save(existingMerchant);
        }).orElseThrow(() -> new RuntimeException("Merchant not found"));

        return convertToDto(updatedMerchant);
    }

    private MerchantResponseDto convertToDto(Merchant merchant) {

        MerchantResponseDto dto1 = new MerchantResponseDto();
        dto1.setId(merchant.getId());
        dto1.setCompanyName(merchant.getCompanyName());
        dto1.setAddress(merchant.getAddress());
        dto1.setBusinessType(merchant.getBusinessType());
        dto1.setEmail(merchant.getEmail());
        dto1.setPhoneNumber(merchant.getPhoneNumber());
        dto1.setBankAccountDetails(merchant.getBankAccountDetails());
        return dto1;
    }

}
