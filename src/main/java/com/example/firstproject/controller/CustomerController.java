package com.example.firstproject.controller;

import java.util.List;
import com.example.firstproject.controller.dto.customer.CreateCustomerDto;
import com.example.firstproject.controller.dto.customer.CreateCustomerResponseDto;
import com.example.firstproject.controller.dto.customer.GetCustomerDto;
import com.example.firstproject.controller.dto.customer.UpdateCustomerDto;
import com.example.firstproject.service.CustomerService;
import com.example.firstproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public CreateCustomerResponseDto createCustomer(@RequestBody CreateCustomerResponseDto createCustomerResponseDto) {
        return customerService.createCustomer(createCustomerResponseDto);
    }

    @GetMapping("/me")
    public GetCustomerDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size parameters");
        }

        try {
            Page<GetCustomerDto> customerPage = customerService.getAllCustomersPaged(page, size, sort, direction);
            return ResponseEntity.ok(customerPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @customerService.getCustomerById(#id).getEmail()")
    public GetCustomerDto updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerDto updateCustomerDto) {
        return customerService.updateCustomer(id, updateCustomerDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @customerService.getCustomerById(#id).getEmail()")
    public GetCustomerDto partialUpdateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerDto updateCustomerDto) {
        return customerService.updateCustomer(id, updateCustomerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @customerService.getCustomerById(#id).getEmail()")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
