package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.WarehouseRequestDTO;
import com.logisticsapp.dto.response.WarehouseResponseDTO;
import com.logisticsapp.entity.Warehouse;
import org.springframework.stereotype.Component;

// mapper - converts warehouse between entity and DTO (request in, response out)
@Component
public class WarehouseMapper {

    // converts WarehouseRequestDTO into a new Warehouse entity
    public Warehouse toEntity(WarehouseRequestDTO dto) {
        Warehouse warehouse = new Warehouse();
        updateEntity(dto, warehouse);
        return warehouse;
    }

    // copies the DTO fields into an existing Warehouse entity
    public void updateEntity(WarehouseRequestDTO dto, Warehouse warehouse) {
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
    }

    // converts a Warehouse entity into WarehouseResponseDTO
    public WarehouseResponseDTO toResponse(Warehouse warehouse) {
        WarehouseResponseDTO dto = new WarehouseResponseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setLocation(warehouse.getLocation());
        dto.setCapacity(warehouse.getCapacity());
        return dto;
    }
}