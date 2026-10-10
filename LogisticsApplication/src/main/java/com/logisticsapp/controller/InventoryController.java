package com.logisticsapp.controller;

import com.logisticsapp.dto.request.InventoryRequestDTO;
import com.logisticsapp.dto.response.InventoryResponseDTO;
import com.logisticsapp.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller - /api/inventory endpoints for inventory item
@Tag(name = "Inventory", description = "Manage stock items inside warehouses")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // POST /api/inventory - creates a new inventory item (checked with @Valid)
    @Operation(summary = "Add an inventory item (warehouse capacity is checked)")
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(@Valid @RequestBody InventoryRequestDTO dto) {
        return new ResponseEntity<>(inventoryService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/inventory - lists inventory item records page by page with sorting
    @Operation(summary = "List inventory items (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<InventoryResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(inventoryService.getAll(page, size, sortBy, direction));
    }

    // GET /api/inventory/low-stock - items with quantity below the limit (native query)
    @Operation(summary = "List items with quantity below a limit (native query)")
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> lowStock(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(inventoryService.getLowStock(limit));
    }

    // GET /api/inventory/{id} - gets one inventory item by id
    @Operation(summary = "Get an inventory item by id")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    // PUT /api/inventory/{id} - updates an existing inventory item
    @Operation(summary = "Update an inventory item (warehouse capacity is checked)")
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.update(id, dto));
    }

    // DELETE /api/inventory/{id} - deletes an inventory item (ADMIN only)
    @Operation(summary = "Delete an inventory item (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.ok("Inventory item deleted successfully");
    }
}