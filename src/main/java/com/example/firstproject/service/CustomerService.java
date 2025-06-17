package com.example.firstproject.service;

import com.example.firstproject.controller.dto.customer.CreateCustomerDto;
import com.example.firstproject.controller.dto.customer.CreateCustomerResponseDto;
import com.example.firstproject.controller.dto.customer.CustomerDto;
import com.example.firstproject.controller.dto.customer.GetCustomerDto;
import com.example.firstproject.controller.dto.customer.UpdateCustomerDto;
import com.example.firstproject.service.models.CustomerModel;
import com.example.firstproject.structure.entity.CustomerEntity;
import com.example.firstproject.structure.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<GetCustomerDto> getAllCustomers() {
        var customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> new GetCustomerDto(customer.getId(),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getEmail(),
                        customer.getShopcart()))
                .toList();
    }

    public Page<GetCustomerDto> getAllCustomersPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CustomerEntity> customersPage = customerRepository.findAll(pageable);

        return customersPage.map(customer -> 
                new GetCustomerDto(
                    customer.getId(), 
                    customer.getName(), 
                    customer.getSurname(), 
                    customer.getEmail(), 
                    customer.getShopcart()
                )
        );
    }

    public List<GetCustomerDto> getCustomersByName(String name) {
        var customers = customerRepository.findAll().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .toList();
        return customers.stream()
                .map(customer -> new GetCustomerDto(customer.getId(),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getEmail(),
                        customer.getShopcart()))
                .toList();
    }

    public GetCustomerDto getCustomerById(Long id) {
        var customerEntity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer hasn't been found"));
        return new GetCustomerDto(customerEntity.getId(), customerEntity.getName(), customerEntity.getSurname(), customerEntity.getEmail(), customerEntity.getShopcart());
    }

    public GetCustomerDto getCustomerByEmail(String email) {
        var customerEntity = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer hasn't been found"));
        return new GetCustomerDto(customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getSurname(),
                customerEntity.getEmail(),
                customerEntity.getShopcart());
    }

    public CreateCustomerResponseDto createCustomer(CreateCustomerResponseDto customer) {
        var customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPassword(passwordEncoder.encode(customer.getPassword()));
        var savedCustomer = customerRepository.save(customerEntity);

        return new CreateCustomerResponseDto(savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getSurname(),
                savedCustomer.getEmail(),
                savedCustomer.getShopcart());
    }

    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    public GetCustomerDto updateCustomer(Long id, UpdateCustomerDto updateCustomerDto) {
        var customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (updateCustomerDto.getName() != null) {
            customerEntity.setName(updateCustomerDto.getName());
        }

        if (updateCustomerDto.getSurname() != null) {
            customerEntity.setSurname(updateCustomerDto.getSurname());
        }

        if (updateCustomerDto.getEmail() != null) {
            customerEntity.setEmail(updateCustomerDto.getEmail());
        }

        if (updateCustomerDto.getPassword() != null) {
            customerEntity.setPassword(passwordEncoder.encode(updateCustomerDto.getPassword()));
        }

        if (updateCustomerDto.getShopcart() != null) {
            customerEntity.setShopcart(updateCustomerDto.getShopcart());
        }

        var savedCustomer = customerRepository.save(customerEntity);

        return new GetCustomerDto(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getSurname(),
                savedCustomer.getEmail(),
                savedCustomer.getShopcart()
        );
    }

    public CustomerDto create(CreateCustomerDto customer) {
        var customerModel = new CustomerModel(null, customer.getName(), customer.getSurname(), customer.getEmail());

        var customerEntity = new CustomerEntity();
        customerEntity.setName(customerModel.getName());
        customerEntity.setSurname(customerModel.getSurname());
        customerEntity.setEmail(customerModel.getEmail());

        customerRepository.save(customerEntity);

        return new CustomerDto(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getSurname(),
                customerEntity.getEmail()
        );
    }
}
