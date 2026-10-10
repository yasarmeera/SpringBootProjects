package com.logisticsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// entity - inventory table (many items belong to one warehouse, audit columns come from Auditable)
@Entity
@Table(name = "inventory")
@Getter
@Setter
public class Inventory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    // keeps the old name used by InventoryMapper (value comes from Auditable)
    public LocalDateTime getLastUpdated() {
        return getLastModifiedAt();
    }
}