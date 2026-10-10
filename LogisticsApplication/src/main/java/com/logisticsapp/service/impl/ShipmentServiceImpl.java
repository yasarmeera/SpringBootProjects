package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.ShipmentRequestDTO;
import com.logisticsapp.dto.response.ShipmentResponseDTO;
import com.logisticsapp.entity.Customer;
import com.logisticsapp.entity.Shipment;
import com.logisticsapp.enums.ShipmentStatus;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.ShipmentMapper;
import com.logisticsapp.repository.CustomerRepository;
import com.logisticsapp.repository.ShipmentRepository;
import com.logisticsapp.service.ShipmentService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// service implementation - business logic for shipment (logging, transactions)
@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    // saves a new shipment inside a transaction (a new shipment always starts as PENDING)
    @Override
    @Transactional
    public ShipmentResponseDTO create(ShipmentRequestDTO dto) {
        Shipment shipment = shipmentMapper.toEntity(dto);
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setCustomer(findCustomer(dto.getCustomerId()));
        shipment.setShipmentDate(LocalDateTime.now());
        Shipment saved = shipmentRepository.save(shipment);
        log.info("Shipment created with id {}", saved.getId());
        return shipmentMapper.toResponse(saved);
    }

    // returns shipment records page by page with sorting
    @Override
    public Page<ShipmentResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return shipmentRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(shipmentMapper::toResponse);
    }

    // finds one shipment by id (404 if missing)
    @Override
    public ShipmentResponseDTO getById(Long id) {
        return shipmentMapper.toResponse(findShipment(id));
    }

    // updates an existing shipment inside a transaction (the status change must be allowed)
    @Override
    @Transactional
    public ShipmentResponseDTO update(Long id, ShipmentRequestDTO dto) {
        Shipment shipment = findShipment(id);
        shipment.getStatus().validateChangeTo(dto.getStatus());
        shipmentMapper.updateEntity(dto, shipment);
        shipment.setCustomer(findCustomer(dto.getCustomerId()));
        log.info("Shipment {} updated", id);
        return shipmentMapper.toResponse(shipmentRepository.save(shipment));
    }

    // deletes a shipment inside a transaction
    @Override
    @Transactional
    public void delete(Long id) {
        shipmentRepository.delete(findShipment(id));
        log.info("Shipment {} deleted", id);
    }

    // returns shipments with the given status (JPQL query)
    @Override
    public List<ShipmentResponseDTO> getByStatus(ShipmentStatus status) {
        return shipmentRepository.findByStatusJpql(status).stream().map(shipmentMapper::toResponse).toList();
    }

    // finds the shipment by id or throws ResourceNotFoundException
    private Shipment findShipment(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id " + id));
    }

    // finds the customer by id or throws ResourceNotFoundException
    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }
}