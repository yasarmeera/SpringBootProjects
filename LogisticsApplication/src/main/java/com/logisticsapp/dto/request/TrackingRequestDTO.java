package com.logisticsapp.dto.request;

import com.logisticsapp.enums.ShipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// request DTO - data the client sends to create or update a tracking entry (validation rules are here)
@Data
public class TrackingRequestDTO {

    @NotNull(message = "Shipment id is required")
    private Long shipmentId;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Status is required")
    private ShipmentStatus status;
}