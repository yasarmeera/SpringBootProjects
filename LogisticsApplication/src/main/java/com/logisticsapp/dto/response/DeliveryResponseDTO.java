package com.logisticsapp.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

// response DTO - delivery data the API returns (set by the server, no validation needed)
@Data
public class DeliveryResponseDTO {

    private Long id;

    private Long shipmentId;

    private String deliveredBy;

    private String status;

    private LocalDateTime deliveryDate;
}