package com.example.firstproject.service;

import com.example.firstproject.controller.dto.login.LoginRequestDto;
import com.example.firstproject.controller.dto.login.LoginResponseDto;
import com.example.firstproject.structure.repository.CustomerRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    public AuthService(CustomerRepository customerRepository, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var customer = customerRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("Customer not found :c"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(loginRequestDto.getPassword(), customer.getPassword())){
            throw new RuntimeException("Wrong login and/or password");
        }


        return new LoginResponseDto(jwtService.createToken(customer.getEmail()));

    }
}