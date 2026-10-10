package com.logisticsapp.scheduler;

import com.logisticsapp.enums.ShipmentStatus;
import com.logisticsapp.repository.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// scheduler - runs a task automatically every 30 minutes (cron never repeats missed runs)
@Component
@Slf4j
public class ShipmentScheduler {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // runs at minute 0 and minute 30 of every hour and logs the pending / in-transit shipment counts
    @Scheduled(cron = "0 */30 * * * *")
    public void logShipmentSummary() {
        long pending = shipmentRepository.countByStatus(ShipmentStatus.PENDING);
        long inTransit = shipmentRepository.countByStatus(ShipmentStatus.IN_TRANSIT);
        log.info("Scheduler -> Pending shipments: {}, In transit: {}", pending, inTransit);
    }
}