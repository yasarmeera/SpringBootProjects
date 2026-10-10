package com.logisticsapp.controller;

import com.logisticsapp.dto.request.CustomerRequestDTO;
import com.logisticsapp.dto.response.CustomerResponseDTO;
import com.logisticsapp.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST controller - /api/customers endpoints for customer
@Tag(name = "Customers", description = "Create, view, update and delete customers")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // POST /api/customers - creates a new customer (checked with @Valid)
    @Operation(summary = "Create a customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO dto) {
        return new ResponseEntity<>(customerService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/customers - lists customer records page by page with sorting
    @Operation(summary = "List customers (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(customerService.getAll(page, size, sortBy, direction));
    }

    // GET /api/customers/{id} - gets one customer by id
    @Operation(summary = "Get a customer by id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    // PUT /api/customers/{id} - updates an existing customer
    @Operation(summary = "Update a customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    // DELETE /api/customers/{id} - deletes a customer (ADMIN only)
    @Operation(summary = "Delete a customer (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}