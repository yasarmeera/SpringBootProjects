package com.logisticsapp.repository;

import com.logisticsapp.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

// repository - database access for delivery (Spring Data JPA)
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // true if a delivery already exists for this shipment
    boolean existsByShipmentId(Long shipmentId);
}