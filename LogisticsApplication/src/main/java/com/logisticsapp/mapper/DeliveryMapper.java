package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.DeliveryRequestDTO;
import com.logisticsapp.dto.response.DeliveryResponseDTO;
import com.logisticsapp.entity.Delivery;
import org.springframework.stereotype.Component;

// mapper - converts delivery between entity and DTO (request in, response out)
@Component
public class DeliveryMapper {

    // converts DeliveryRequestDTO into a new Delivery entity
    public Delivery toEntity(DeliveryRequestDTO dto) {
        Delivery delivery = new Delivery();
        updateEntity(dto, delivery);
        return delivery;
    }

    // copies the DTO fields into an existing Delivery entity
    public void updateEntity(DeliveryRequestDTO dto, Delivery delivery) {
        delivery.setDeliveredBy(dto.getDeliveredBy());
        delivery.setStatus(dto.getStatus());
    }

    // converts a Delivery entity into DeliveryResponseDTO
    public DeliveryResponseDTO toResponse(Delivery delivery) {
        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.setId(delivery.getId());
        dto.setShipmentId(delivery.getShipment().getId());
        dto.setDeliveredBy(delivery.getDeliveredBy());
        dto.setStatus(delivery.getStatus());
        dto.setDeliveryDate(delivery.getDeliveryDate());
        return dto;
    }
}