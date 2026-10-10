package com.logisticsapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// request DTO - data the client sends to create or update an inventory item (validation rules are here)
@Data
public class InventoryRequestDTO {

    @NotNull(message = "Warehouse id is required")
    private Long warehouseId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}