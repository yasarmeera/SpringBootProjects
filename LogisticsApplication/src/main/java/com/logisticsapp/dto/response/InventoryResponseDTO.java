package com.logisticsapp.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

// response DTO - inventory item data the API returns (set by the server, no validation needed)
@Data
public class InventoryResponseDTO {

    private Long id;

    private Long warehouseId;

    private String productName;

    private Integer quantity;

    private LocalDateTime lastUpdated;
}