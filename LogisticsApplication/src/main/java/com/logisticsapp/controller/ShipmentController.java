package com.logisticsapp.controller;

import com.logisticsapp.dto.request.ShipmentRequestDTO;
import com.logisticsapp.dto.response.ShipmentResponseDTO;
import com.logisticsapp.enums.ShipmentStatus;
import com.logisticsapp.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller - /api/shipments endpoints for shipment
@Tag(name = "Shipments", description = "Create and manage shipments")
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    // POST /api/shipments - creates a new shipment (checked with @Valid)
    @Operation(summary = "Create a shipment (starts as PENDING)")
    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> create(@Valid @RequestBody ShipmentRequestDTO dto) {
        return new ResponseEntity<>(shipmentService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/shipments - lists shipment records page by page with sorting
    @Operation(summary = "List shipments (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<ShipmentResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(shipmentService.getAll(page, size, sortBy, direction));
    }

    // GET /api/shipments/status/{status} - shipments with that status (JPQL query)
    @Operation(summary = "List shipments by status (JPQL query)")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ShipmentResponseDTO>> getByStatus(@PathVariable ShipmentStatus status) {
        return ResponseEntity.ok(shipmentService.getByStatus(status));
    }

    // GET /api/shipments/{id} - gets one shipment by id
    @Operation(summary = "Get a shipment by id")
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getById(id));
    }

    // PUT /api/shipments/{id} - updates an existing shipment
    @Operation(summary = "Update a shipment (status change rules apply)")
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody ShipmentRequestDTO dto) {
        return ResponseEntity.ok(shipmentService.update(id, dto));
    }

    // DELETE /api/shipments/{id} - deletes a shipment (ADMIN only)
    @Operation(summary = "Delete a shipment (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        shipmentService.delete(id);
        return ResponseEntity.ok("Shipment deleted successfully");
    }
}