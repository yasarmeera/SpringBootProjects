package com.logisticsapp.service;

import com.logisticsapp.dto.request.CustomerRequestDTO;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;

// service interface - declares the customer operations
public interface CustomerService {

    // saves a new customer
    CustomerResponseDTO create(CustomerRequestDTO dto);

    // returns customer records page by page with sorting
    Page<CustomerResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one customer by id
    CustomerResponseDTO getById(Long id);

    // updates an existing customer
    CustomerResponseDTO update(Long id, CustomerRequestDTO dto);

    // deletes a customer by id
    void delete(Long id);
}