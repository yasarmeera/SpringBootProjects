package com.logisticsapp.service;

import com.logisticsapp.dto.request.TrackingRequestDTO;
import com.logisticsapp.dto.response.TrackingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// service interface - declares the tracking entry operations
public interface TrackingService {

    // saves a new tracking entry
    TrackingResponseDTO create(TrackingRequestDTO dto);

    // returns tracking entry records page by page with sorting
    Page<TrackingResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one tracking entry by id
    TrackingResponseDTO getById(Long id);

    // updates an existing tracking entry
    TrackingResponseDTO update(Long id, TrackingRequestDTO dto);

    // deletes a tracking entry by id
    void delete(Long id);

    // returns the tracking history of one shipment
    List<TrackingResponseDTO> getByShipment(Long shipmentId);
}