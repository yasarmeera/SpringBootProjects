package com.logisticsapp.repository;

import com.logisticsapp.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// repository - database access for inventory item (Spring Data JPA)
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // native SQL query - inventory items with quantity below the given maximum
    @Query(value = "SELECT * FROM inventory WHERE quantity < :maxQty", nativeQuery = true)
    List<Inventory> findLowStock(@Param("maxQty") int maxQty);

    // total quantity already stored in a warehouse (the item being edited is excluded)
    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.warehouse.id = :warehouseId AND i.id <> :excludeId")
    long sumQuantityByWarehouse(@Param("warehouseId") Long warehouseId, @Param("excludeId") Long excludeId);
}