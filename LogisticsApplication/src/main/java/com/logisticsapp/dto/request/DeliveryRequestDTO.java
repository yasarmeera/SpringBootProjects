package com.logisticsapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// request DTO - delivery data coming from the user (validation rules are here)
@Data
public class DeliveryRequestDTO {

    @NotNull(message = "Shipment id is required")
    private Long shipmentId;

    @NotBlank(message = "Delivered by is required")
    private String deliveredBy;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "DELIVERED", message = "Status must be DELIVERED")
    private String status;
}