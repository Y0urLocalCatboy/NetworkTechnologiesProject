package com.example.firstproject.controller;


import com.example.firstproject.controller.dto.customer.CreateCustomerDto;
import com.example.firstproject.controller.dto.customer.CreateCustomerResponseDto;
import com.example.firstproject.controller.dto.customer.GetCustomerDto;
import com.example.firstproject.service.CustomerService;
import com.example.firstproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping(value = "/{id}")
    public GetCustomerDto getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }


    @PostMapping()
    public CreateCustomerResponseDto createCustomer(@RequestBody CreateCustomerResponseDto createCustomerResponseDto) {
        return customerService.createCustomer(createCustomerResponseDto);
    }
}
