package com.logisticsapp.controller;

import com.logisticsapp.dto.request.DeliveryRequestDTO;
import com.logisticsapp.dto.response.DeliveryResponseDTO;
import com.logisticsapp.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST controller - /api/delivery endpoints for delivery
@Tag(name = "Delivery", description = "Record deliveries of shipments")
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // POST /api/delivery - creates a new delivery (checked with @Valid)
    @Operation(summary = "Record a delivery (marks the shipment DELIVERED)")
    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> create(@Valid @RequestBody DeliveryRequestDTO dto) {
        return new ResponseEntity<>(deliveryService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/delivery - lists delivery records page by page with sorting
    @Operation(summary = "List deliveries (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<DeliveryResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(deliveryService.getAll(page, size, sortBy, direction));
    }

    // GET /api/delivery/{id} - gets one delivery by id
    @Operation(summary = "Get a delivery by id")
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getById(id));
    }

    // PUT /api/delivery/{id} - updates an existing delivery
    @Operation(summary = "Update a delivery")
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody DeliveryRequestDTO dto) {
        return ResponseEntity.ok(deliveryService.update(id, dto));
    }

    // DELETE /api/delivery/{id} - deletes a delivery (ADMIN only)
    @Operation(summary = "Delete a delivery (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        deliveryService.delete(id);
        return ResponseEntity.ok("Delivery deleted successfully");
    }
}