package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.ShipmentRequestDTO;
import com.logisticsapp.dto.response.ShipmentResponseDTO;
import com.logisticsapp.entity.Shipment;
import org.springframework.stereotype.Component;

// mapper - converts shipment between entity and DTO (request in, response out)
@Component
public class ShipmentMapper {

    // converts ShipmentRequestDTO into a new Shipment entity
    public Shipment toEntity(ShipmentRequestDTO dto) {
        Shipment shipment = new Shipment();
        updateEntity(dto, shipment);
        return shipment;
    }

    // copies the DTO fields into an existing Shipment entity
    public void updateEntity(ShipmentRequestDTO dto, Shipment shipment) {
        shipment.setOrigin(dto.getOrigin());
        shipment.setDestination(dto.getDestination());
        if (dto.getStatus() != null) {
            shipment.setStatus(dto.getStatus());
        }
    }

    // converts a Shipment entity into ShipmentResponseDTO
    public ShipmentResponseDTO toResponse(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.setId(shipment.getId());
        dto.setCustomerId(shipment.getCustomer().getId());
        dto.setOrigin(shipment.getOrigin());
        dto.setDestination(shipment.getDestination());
        dto.setStatus(shipment.getStatus());
        dto.setShipmentDate(shipment.getShipmentDate());
        return dto;
    }
}