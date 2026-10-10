package com.logisticsapp.service.impl;

import com.logisticsapp.dto.request.TrackingRequestDTO;
import com.logisticsapp.dto.response.TrackingResponseDTO;
import com.logisticsapp.entity.Shipment;
import com.logisticsapp.entity.Tracking;
import com.logisticsapp.exception.BadRequestException;
import com.logisticsapp.exception.ResourceNotFoundException;
import com.logisticsapp.mapper.TrackingMapper;
import com.logisticsapp.repository.ShipmentRepository;
import com.logisticsapp.repository.TrackingRepository;
import com.logisticsapp.service.TrackingService;
import com.logisticsapp.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// service implementation - business logic for tracking entry (logging, transactions)
@Service
@Slf4j
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private TrackingMapper trackingMapper;

    // saves the tracking entry and updates the shipment status in one transaction
    @Override
    @Transactional
    public TrackingResponseDTO create(TrackingRequestDTO dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id " + dto.getShipmentId()));
        shipment.getStatus().validateChangeTo(dto.getStatus());

        Tracking tracking = trackingMapper.toEntity(dto);
        tracking.setShipment(shipment);
        tracking.setTimestamp(LocalDateTime.now());
        shipment.setStatus(dto.getStatus());

        Tracking saved = trackingRepository.save(tracking);
        log.info("Tracking {} added for shipment {}", saved.getId(), shipment.getId());
        return trackingMapper.toResponse(saved);
    }

    // returns tracking entry records page by page with sorting
    @Override
    public Page<TrackingResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        return trackingRepository.findAll(PageUtil.of(page, size, sortBy, direction)).map(trackingMapper::toResponse);
    }

    // finds one tracking entry by id (404 if missing)
    @Override
    public TrackingResponseDTO getById(Long id) {
        return trackingMapper.toResponse(findTracking(id));
    }

    // updates the location of a tracking entry (the status cannot be changed here)
    @Override
    @Transactional
    public TrackingResponseDTO update(Long id, TrackingRequestDTO dto) {
        Tracking tracking = findTracking(id);
        if (tracking.getStatus() != dto.getStatus()) {
            throw new BadRequestException("Tracking status cannot be changed - add a new tracking update instead");
        }
        trackingMapper.updateEntity(dto, tracking);
        log.info("Tracking {} updated", id);
        return trackingMapper.toResponse(trackingRepository.save(tracking));
    }

    // deletes a tracking entry inside a transaction
    @Override
    @Transactional
    public void delete(Long id) {
        trackingRepository.delete(findTracking(id));
        log.info("Tracking {} deleted", id);
    }

    // returns the tracking history of a shipment, latest first
    @Override
    public List<TrackingResponseDTO> getByShipment(Long shipmentId) {
        return trackingRepository.findByShipmentIdOrderByTimestampDesc(shipmentId).stream()
                .map(trackingMapper::toResponse).toList();
    }

    // finds the tracking entry by id or throws ResourceNotFoundException
    private Tracking findTracking(Long id) {
        return trackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking not found with id " + id));
    }
}