package com.logisticsapp.service;

import com.logisticsapp.dto.request.WarehouseRequestDTO;
import com.logisticsapp.dto.response.WarehouseResponseDTO;
import org.springframework.data.domain.Page;

// service interface - declares the warehouse operations
public interface WarehouseService {

    // saves a new warehouse
    WarehouseResponseDTO create(WarehouseRequestDTO dto);

    // returns warehouse records page by page with sorting
    Page<WarehouseResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one warehouse by id
    WarehouseResponseDTO getById(Long id);

    // updates an existing warehouse
    WarehouseResponseDTO update(Long id, WarehouseRequestDTO dto);

    // deletes a warehouse by id
    void delete(Long id);
}