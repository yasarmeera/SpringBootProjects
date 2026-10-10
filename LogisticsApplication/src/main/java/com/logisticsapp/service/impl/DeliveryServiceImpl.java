package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.DeliveryRequestDTO;
import com.logisticsapp.dto.response.DeliveryResponseDTO;
import com.logisticsapp.entity.Delivery;
import com.logisticsapp.entity.Shipment;
import com.logisticsapp.enums.ShipmentStatus;
import com.logisticsapp.exception.BadRequestException;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.DeliveryMapper;
import com.logisticsapp.repository.DeliveryRepository;
import com.logisticsapp.repository.ShipmentRepository;
import com.logisticsapp.service.DeliveryService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// service implementation - business logic for delivery (logging, transactions, status rules)
@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    // saves the delivery and marks the shipment DELIVERED in one transaction (one delivery per shipment)
    @Override
    @Transactional
    public DeliveryResponseDTO create(DeliveryRequestDTO dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id " + dto.getShipmentId()));

        if (deliveryRepository.existsByShipmentId(dto.getShipmentId())) {
            throw new BadRequestException("Delivery already recorded for shipment " + dto.getShipmentId());
        }

        shipment.getStatus().validateChangeTo(ShipmentStatus.DELIVERED);

        Delivery delivery = deliveryMapper.toEntity(dto);
        delivery.setShipment(shipment);
        delivery.setDeliveryDate(LocalDateTime.now());
        shipment.setStatus(ShipmentStatus.DELIVERED);

        Delivery saved = deliveryRepository.save(delivery);
        log.info("Delivery {} recorded for shipment {}", saved.getId(), shipment.getId());
        return deliveryMapper.toResponse(saved);
    }

    // returns delivery records page by page with sorting
    @Override
    public Page<DeliveryResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return deliveryRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(deliveryMapper::toResponse);
    }

    // finds one delivery by id (404 if missing)
    @Override
    public DeliveryResponseDTO getById(Long id) {
        return deliveryMapper.toResponse(findDelivery(id));
    }

    // updates an existing delivery inside a transaction
    @Override
    @Transactional
    public DeliveryResponseDTO update(Long id, DeliveryRequestDTO dto) {
        Delivery delivery = findDelivery(id);
        deliveryMapper.updateEntity(dto, delivery);
        log.info("Delivery {} updated", id);
        return deliveryMapper.toResponse(deliveryRepository.save(delivery));
    }

    // deletes a delivery inside a transaction
    @Override
    @Transactional
    public void delete(Long id) {
        deliveryRepository.delete(findDelivery(id));
        log.info("Delivery {} deleted", id);
    }

    // finds the delivery by id or throws ResourceNotFoundException
    private Delivery findDelivery(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id " + id));
    }
}