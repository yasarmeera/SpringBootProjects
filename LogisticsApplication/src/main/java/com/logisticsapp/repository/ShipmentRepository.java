package com.logisticsapp.repository;

import com.logisticsapp.entity.Shipment;
import com.logisticsapp.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// repository - database access for shipment (Spring Data JPA)
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    // JPQL query - shipments with the given status
    @Query("SELECT s FROM Shipment s WHERE s.status = :status")
    List<Shipment> findByStatusJpql(@Param("status") ShipmentStatus status);

    // counts shipments with the given status
    long countByStatus(ShipmentStatus status);
}