package com.logisticsapp.mapper;

import com.logisticsapp.dto.request.TrackingRequestDTO;
import com.logisticsapp.dto.response.TrackingResponseDTO;
import com.logisticsapp.entity.Tracking;
import org.springframework.stereotype.Component;

// mapper - converts tracking entry between entity and DTO (request in, response out)
@Component
public class TrackingMapper {

    // converts TrackingRequestDTO into a new Tracking entity
    public Tracking toEntity(TrackingRequestDTO dto) {
        Tracking tracking = new Tracking();
        updateEntity(dto, tracking);
        return tracking;
    }

    // copies the DTO fields into an existing Tracking entity
    public void updateEntity(TrackingRequestDTO dto, Tracking tracking) {
        tracking.setLocation(dto.getLocation());
        tracking.setStatus(dto.getStatus());
    }

    // converts a Tracking entity into TrackingResponseDTO
    public TrackingResponseDTO toResponse(Tracking tracking) {
        TrackingResponseDTO dto = new TrackingResponseDTO();
        dto.setId(tracking.getId());
        dto.setShipmentId(tracking.getShipment().getId());
        dto.setLocation(tracking.getLocation());
        dto.setStatus(tracking.getStatus());
        dto.setTimestamp(tracking.getTimestamp());
        return dto;
    }
}