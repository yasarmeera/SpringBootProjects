package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.InventoryRequestDTO;
import com.logisticsapp.dto.response.InventoryResponseDTO;
import com.logisticsapp.entity.Inventory;
import org.springframework.stereotype.Component;

// mapper - converts inventory item between entity and DTO (request in, response out)
@Component
public class InventoryMapper {

    // converts InventoryRequestDTO into a new Inventory entity
    public Inventory toEntity(InventoryRequestDTO dto) {
        Inventory item = new Inventory();
        updateEntity(dto, item);
        return item;
    }

    // copies the DTO fields into an existing Inventory entity
    public void updateEntity(InventoryRequestDTO dto, Inventory item) {
        item.setProductName(dto.getProductName());
        item.setQuantity(dto.getQuantity());
    }

    // converts an Inventory entity into InventoryResponseDTO
    public InventoryResponseDTO toResponse(Inventory item) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(item.getId());
        dto.setWarehouseId(item.getWarehouse().getId());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setLastUpdated(item.getLastUpdated());
        return dto;
    }
}