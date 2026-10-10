package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.CustomerRequestDTO;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import com.logisticsapp.entity.Customer;
import com.logisticsapp.exception.BadRequestException;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.CustomerMapper;
import com.logisticsapp.repository.CustomerRepository;
import com.logisticsapp.service.CustomerService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service implementation - business logic for customer (logging, transactions)
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    // saves a new customer (email must be unique) inside a transaction
    @Override
    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists: " + dto.getEmail());
        }
        Customer saved = customerRepository.save(customerMapper.toEntity(dto));
        log.info("Customer created with id {}", saved.getId());
        return customerMapper.toResponse(saved);
    }

    // returns customer records page by page with sorting
    @Override
    public Page<CustomerResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return customerRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(customerMapper::toResponse);
    }

    // finds one customer by id (result is cached)
    @Override
    @Cacheable(value = "customers", key = "#id")
    public CustomerResponseDTO getById(Long id) {
        log.info("Fetching customer {} from database", id);
        return customerMapper.toResponse(findCustomer(id));
    }

    // updates the customer and refreshes the cache
    @Override
    @Transactional
    @CachePut(value = "customers", key = "#id")
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer customer = findCustomer(id);
        if (customerRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new BadRequestException("Email already exists: " + dto.getEmail());
        }
        customerMapper.updateEntity(dto, customer);
        log.info("Customer {} updated", id);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    // deletes the customer and removes it from the cache
    @Override
    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public void delete(Long id) {
        customerRepository.delete(findCustomer(id));
        log.info("Customer {} deleted", id);
    }

    // finds the customer by id or throws ResourceNotFoundException
    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }
}