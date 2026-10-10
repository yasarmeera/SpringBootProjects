package com.logisticsapp.dto.response;

import com.logisticsapp.enums.ShipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

// response DTO - shipment data the API returns (set by the server, no validation needed)
@Data
public class ShipmentResponseDTO {

    private Long id;

    private Long customerId;

    private String origin;

    private String destination;

    private ShipmentStatus status;

    private LocalDateTime shipmentDate;
}