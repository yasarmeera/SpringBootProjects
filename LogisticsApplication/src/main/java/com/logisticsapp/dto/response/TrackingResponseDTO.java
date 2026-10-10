package com.logisticsapp.dto.response;

import com.logisticsapp.enums.ShipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

// response DTO - tracking entry data the API returns (set by the server, no validation needed)
@Data
public class TrackingResponseDTO {

    private Long id;

    private Long shipmentId;

    private String location;

    private ShipmentStatus status;

    private LocalDateTime timestamp;
}