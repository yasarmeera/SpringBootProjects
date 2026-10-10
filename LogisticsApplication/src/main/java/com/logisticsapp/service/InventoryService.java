package com.logisticsapp.service;

import com.logisticsapp.dto.request.InventoryRequestDTO;
import com.logisticsapp.dto.response.InventoryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// service interface - declares the inventory item operations
public interface InventoryService {

    // saves a new inventory item
    InventoryResponseDTO create(InventoryRequestDTO dto);

    // returns inventory item records page by page with sorting
    Page<InventoryResponseDTO> getAll(int page, int size, String sortBy, String direction);

    // finds one inventory item by id
    InventoryResponseDTO getById(Long id);

    // updates an existing inventory item
    InventoryResponseDTO update(Long id, InventoryRequestDTO dto);

    // deletes an inventory item by id
    void delete(Long id);

    // returns items with quantity below the limit
    List<InventoryResponseDTO> getLowStock(int limit);
}