package com.logisticsapp.service;

import com.logisticsapp.dto.request.DeliveryRequestDTO;
import com.logisticsapp.dto.response.DeliveryResponseDTO;
import org.springframework.data.domain.Page;

// service interface - declares the delivery operations
public interface DeliveryService {

    // saves a new delivery
    DeliveryResponseDTO create(DeliveryRequestDTO dto);

    // returns delivery records page by page with sorting
    Page<DeliveryResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one delivery by id
    DeliveryResponseDTO getById(Long id);

    // updates an existing delivery
    DeliveryResponseDTO update(Long id, DeliveryRequestDTO dto);

    // deletes a delivery by id
    void delete(Long id);
}