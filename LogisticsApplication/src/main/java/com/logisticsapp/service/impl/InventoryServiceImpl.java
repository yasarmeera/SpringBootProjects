package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.InventoryRequestDTO;
import com.logisticsapp.dto.response.InventoryResponseDTO;
import com.logisticsapp.entity.Inventory;
import com.logisticsapp.entity.Warehouse;
import com.logisticsapp.exception.BadRequestException;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.InventoryMapper;
import com.logisticsapp.repository.InventoryRepository;
import com.logisticsapp.repository.WarehouseRepository;
import com.logisticsapp.service.InventoryService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// service implementation - business logic for inventory item (logging, transactions)
@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    // saves a new inventory item inside a transaction (warehouse capacity is checked)
    @Override
    @Transactional
    public InventoryResponseDTO create(InventoryRequestDTO dto) {
        Warehouse warehouse = findWarehouse(dto.getWarehouseId());
        checkCapacity(warehouse, dto.getQuantity(), -1L);

        Inventory item = inventoryMapper.toEntity(dto);
        item.setWarehouse(warehouse);
        Inventory saved = inventoryRepository.save(item);
        log.info("Inventory item created with id {}", saved.getId());
        return inventoryMapper.toResponse(saved);
    }

    // returns inventory item records page by page with sorting
    @Override
    public Page<InventoryResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return inventoryRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(inventoryMapper::toResponse);
    }

    // finds one inventory item by id (404 if missing)
    @Override
    public InventoryResponseDTO getById(Long id) {
        return inventoryMapper.toResponse(findItem(id));
    }

    // updates an existing inventory item inside a transaction (warehouse capacity is checked)
    @Override
    @Transactional
    public InventoryResponseDTO update(Long id, InventoryRequestDTO dto) {
        Inventory item = findItem(id);
        Warehouse warehouse = findWarehouse(dto.getWarehouseId());
        checkCapacity(warehouse, dto.getQuantity(), id);

        inventoryMapper.updateEntity(dto, item);
        item.setWarehouse(warehouse);
        log.info("Inventory item {} updated", id);
        return inventoryMapper.toResponse(inventoryRepository.save(item));
    }

    // deletes an inventory item inside a transaction
    @Override
    @Transactional
    public void delete(Long id) {
        inventoryRepository.delete(findItem(id));
        log.info("Inventory item {} deleted", id);
    }

    // returns items with quantity below the limit (native query)
    @Override
    public List<InventoryResponseDTO> getLowStock(int limit) {
        return inventoryRepository.findLowStock(limit).stream().map(inventoryMapper::toResponse).toList();
    }

    // throws BadRequestException if the warehouse cannot hold the new quantity
    private void checkCapacity(Warehouse warehouse, int newQuantity, Long excludeItemId) {
        if (warehouse.getCapacity() == null) {
            return;
        }
        long used = inventoryRepository.sumQuantityByWarehouse(warehouse.getId(), excludeItemId);
        if (used + newQuantity > warehouse.getCapacity()) {
            throw new BadRequestException("Warehouse capacity exceeded: capacity " + warehouse.getCapacity()
                    + ", already used " + used + ", requested " + newQuantity);
        }
    }

    // finds the inventory item by id or throws ResourceNotFoundException
    private Inventory findItem(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id " + id));
    }

    // finds the warehouse by id or throws ResourceNotFoundException
    private Warehouse findWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id " + id));
    }
}