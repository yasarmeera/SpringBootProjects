package com.logisticsapp.controller;

import com.logisticsapp.dto.request.TrackingRequestDTO;
import com.logisticsapp.dto.response.TrackingResponseDTO;
import com.logisticsapp.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller - /api/tracking endpoints for tracking entry
@Tag(name = "Tracking", description = "Tracking updates of shipments")
@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    // POST /api/tracking - creates a new tracking entry (checked with @Valid)
    @Operation(summary = "Add a tracking entry (also updates the shipment status)")
    @PostMapping
    public ResponseEntity<TrackingResponseDTO> create(@Valid @RequestBody TrackingRequestDTO dto) {
        return new ResponseEntity<>(trackingService.create(dto), HttpStatus.CREATED);
    }

    // GET /api/tracking - lists tracking entry records page by page with sorting
    @Operation(summary = "List tracking entries (paged, sorted)")
    @GetMapping
    public ResponseEntity<Page<TrackingResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(trackingService.getAll(page, size, sortBy, direction));
    }

    // GET /api/tracking/shipment/{shipmentId} - tracking history of one shipment
    @Operation(summary = "Tracking history of one shipment, latest first")
    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<List<TrackingResponseDTO>> getByShipment(@PathVariable Long shipmentId) {
        return ResponseEntity.ok(trackingService.getByShipment(shipmentId));
    }

    // GET /api/tracking/{id} - gets one tracking entry by id
    @Operation(summary = "Get a tracking entry by id")
    @GetMapping("/{id}")
    public ResponseEntity<TrackingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trackingService.getById(id));
    }

    // PUT /api/tracking/{id} - updates an existing tracking entry
    @Operation(summary = "Update a tracking entry")
    @PutMapping("/{id}")
    public ResponseEntity<TrackingResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody TrackingRequestDTO dto) {
        return ResponseEntity.ok(trackingService.update(id, dto));
    }

    // DELETE /api/tracking/{id} - deletes a tracking entry (ADMIN only)
    @Operation(summary = "Delete a tracking entry (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        trackingService.delete(id);
        return ResponseEntity.ok("Tracking entry deleted successfully");
    }
}