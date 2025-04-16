package com.example.firstproject.controller;


import com.example.firstproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    private final JwtService jwtService;

    @Autowired
    public CustomerController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @RequestMapping(value = "/{id}")
    public String getCustomer() {
        // user dto
        // user service
        // user entity
        // user repository
        // return user from db with id
        return "User data";
    }

    @PostMapping()
    public CreateCustomerDto createCustomer(){
        // create user dto
        //crete user in service, validate data
        // return user login dto with token
    }
    @PostMapping("/login")
    public String loginCustomer(){
        // create user dto
        // validate user in service
        // return user login dto with token
    }
}
