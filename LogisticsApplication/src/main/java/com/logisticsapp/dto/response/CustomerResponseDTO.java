package com.logisticsapp.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

// response DTO - customer data the API returns (set by the server, no validation needed)
@Data
public class CustomerResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdAt;
}