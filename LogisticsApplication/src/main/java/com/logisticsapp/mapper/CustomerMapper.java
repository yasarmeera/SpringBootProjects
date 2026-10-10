package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.CustomerRequestDTO;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import com.logisticsapp.entity.Customer;
import org.springframework.stereotype.Component;

// mapper - converts customer between entity and DTO (request in, response out)
@Component
public class CustomerMapper {

    // converts CustomerRequestDTO into a new Customer entity
    public Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        updateEntity(dto, customer);
        return customer;
    }

    // copies the DTO fields into an existing Customer entity
    public void updateEntity(CustomerRequestDTO dto, Customer customer) {
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
    }

    // converts a Customer entity into CustomerResponseDTO
    public CustomerResponseDTO toResponse(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
}