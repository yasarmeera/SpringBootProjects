package com.logisticsapp.repository;

import com.logisticsapp.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository - database access for tracking entry (Spring Data JPA)
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    // tracking entries of one shipment, newest first
    List<Tracking> findByShipmentIdOrderByTimestampDesc(Long shipmentId);
}