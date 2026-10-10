package com.logisticsapp.dto.request;

import com.logisticsapp.enums.ShipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// request DTO - data the client sends to create or update a shipment (validation rules are here)
@Data
public class ShipmentRequestDTO {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    private ShipmentStatus status;
}