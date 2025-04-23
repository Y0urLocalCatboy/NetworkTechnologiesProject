package com.example.firstproject.service;

import com.example.firstproject.controller.dto.login.LoginRequestDto;
import com.example.firstproject.controller.dto.login.LoginResponseDto;
import com.example.firstproject.structure.repository.CustomerRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerRepository customerRepository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var customer = customerRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("Customer not found :c"));

        var doPasswordMatch = passwordEncoder.matches(loginRequestDto.getPassword(), customer.getPassword());

        if(!doPasswordMatch){
            throw new RuntimeException("Wrong login and/or password");
        }


        return new LoginResponseDto(jwtService.createToken(customer.getEmail()));

    }
}