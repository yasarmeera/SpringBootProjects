package com.logisticsapp.dto.response;

import lombok.Data;

// response DTO - warehouse data the API returns (set by the server, no validation needed)
@Data
public class WarehouseResponseDTO {

    private Long id;

    private String name;

    private String location;

    private Integer capacity;
}