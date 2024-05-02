package com.example.paymentSystem.controller;
import com.example.paymentSystem.dto.MerchantRequestDto;
import com.example.paymentSystem.dto.MerchantResponseDto;
import com.example.paymentSystem.service.MerchantService;
import com.example.paymentSystem.utill.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
public class Merchant {
    private final MerchantService merchantService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public Merchant(MerchantService merchantService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.merchantService = merchantService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<MerchantResponseDto> signup(@RequestBody MerchantRequestDto merchantDto) {
        MerchantResponseDto createdMerchant = merchantService.createMerchant(merchantDto);
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MerchantRequestDto merchantDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(merchantDto.getEmail(), merchantDto.getPassword())
            );
            String token = jwtUtil.generateToken(merchantDto.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantResponseDto> updateMerchant(@PathVariable Long id, @RequestBody MerchantRequestDto updatedMerchantDto) {
        MerchantResponseDto updatedMerchant = merchantService.updateMerchant(id, updatedMerchantDto);
        return new ResponseEntity<>(updatedMerchant, HttpStatus.OK);
    }
}
