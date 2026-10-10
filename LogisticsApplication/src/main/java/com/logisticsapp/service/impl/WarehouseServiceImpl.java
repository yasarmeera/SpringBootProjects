package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.WarehouseRequestDTO;
import com.logisticsapp.dto.response.WarehouseResponseDTO;
import com.logisticsapp.entity.Warehouse;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.WarehouseMapper;
import com.logisticsapp.repository.WarehouseRepository;
import com.logisticsapp.service.WarehouseService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service implementation - business logic for warehouse (logging, transactions)
@Service
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseMapper warehouseMapper;

    // saves a new warehouse inside a transaction
    @Override
    @Transactional
    public WarehouseResponseDTO create(WarehouseRequestDTO dto) {
        Warehouse saved = warehouseRepository.save(warehouseMapper.toEntity(dto));
        log.info("Warehouse created with id {}", saved.getId());
        return warehouseMapper.toResponse(saved);
    }

    // returns warehouse records page by page with sorting
    @Override
    public Page<WarehouseResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return warehouseRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(warehouseMapper::toResponse);
    }

    // finds one warehouse by id (404 if missing)
    @Override
    public WarehouseResponseDTO getById(Long id) {
        return warehouseMapper.toResponse(findWarehouse(id));
    }

    // updates an existing warehouse inside a transaction
    @Override
    @Transactional
    public WarehouseResponseDTO update(Long id, WarehouseRequestDTO dto) {
        Warehouse warehouse = findWarehouse(id);
        warehouseMapper.updateEntity(dto, warehouse);
        log.info("Warehouse {} updated", id);
        return warehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    // deletes a warehouse inside a transaction
    @Override
    @Transactional
    public void delete(Long id) {
        warehouseRepository.delete(findWarehouse(id));
        log.info("Warehouse {} deleted", id);
    }

    // finds the warehouse by id or throws ResourceNotFoundException
    private Warehouse findWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id " + id));
    }
}