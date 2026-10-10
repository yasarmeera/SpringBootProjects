package com.logisticsapp.repository;

import com.logisticsapp.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

// repository - database access for warehouse (Spring Data JPA)
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}