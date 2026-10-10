package com.logisticsapp.controller;

import com.logisticsapp.dto.request.WarehouseRequestDTO;
import com.logisticsapp.dto.response.WarehouseResponseDTO;
import com.logisticsapp.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST controller - /api/warehouses endpoints for warehouse
@Tag(name = "Warehouses", description = "Create, view, update and delete warehouses")
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    // POST /api/warehouses - creates a new warehouse (checked with @Valid)
    @Operation(summary = "Create a warehouse")
    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> create(@Valid @RequestBody WarehouseRequestDTO dto) {
        return new ResponseEntity<>(warehouseService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/warehouses - lists warehouse records page by page with sorting
    @Operation(summary = "List warehouses (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<WarehouseResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(warehouseService.getAll(page, size, sortBy, direction));
    }

    // GET /api/warehouses/{id} - gets one warehouse by id
    @Operation(summary = "Get a warehouse by id")
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getById(id));
    }

    // PUT /api/warehouses/{id} - updates an existing warehouse
    @Operation(summary = "Update a warehouse")
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody WarehouseRequestDTO dto) {
        return ResponseEntity.ok(warehouseService.update(id, dto));
    }

    // DELETE /api/warehouses/{id} - deletes a warehouse (ADMIN only)
    @Operation(summary = "Delete a warehouse (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return ResponseEntity.ok("Warehouse deleted successfully");
    }
}