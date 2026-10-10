package com.logisticsapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.logisticsapp.dto.request.CustomerRequestDTO;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import com.logisticsapp.entity.Customer;
import com.logisticsapp.mapper.CustomerMapper;
import com.logisticsapp.repository.CustomerRepository;
import com.logisticsapp.service.impl.CustomerServiceImpl;

// service test - checks the customer business logic with a fake repository (no database needed)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    private CustomerMapper customerMapper = new CustomerMapper();

    @InjectMocks
    private CustomerServiceImpl customerService;

    // test case 1: a new customer with a fresh email is saved and returned
    @Test
    void createCustomer_success() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("Ravi");
        dto.setEmail("ravi@mail.com");
        dto.setPhone("9876543210");
        dto.setAddress("Main Street");

        when(customerRepository.existsByEmail("ravi@mail.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> {
            Customer c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        CustomerResponseDTO result = customerService.create(dto);

        assertEquals(1L, result.getId());
        assertEquals("Ravi", result.getName());
    }
}