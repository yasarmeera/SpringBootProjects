package com.logisticsapp.service;

import com.logisticsapp.dto.request.ShipmentRequestDTO;
import com.logisticsapp.dto.response.ShipmentResponseDTO;
import com.logisticsapp.enums.ShipmentStatus;
import org.springframework.data.domain.Page;

import java.util.List;

// service interface - declares the shipment operations
public interface ShipmentService {

    // saves a new shipment
    ShipmentResponseDTO create(ShipmentRequestDTO dto);

    // returns shipment records page by page with sorting
    Page<ShipmentResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one shipment by id
    ShipmentResponseDTO getById(Long id);

    // updates an existing shipment
    ShipmentResponseDTO update(Long id, ShipmentRequestDTO dto);

    // deletes a shipment by id
    void delete(Long id);

    // returns shipments with the given status
    List<ShipmentResponseDTO> getByStatus(ShipmentStatus status);
}