package com.logisticsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// entity - warehouses table (one warehouse -> many inventory items)
@Entity
@Table(name = "warehouses")
@Getter
@Setter
public class Warehouse extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private Integer capacity;

    // One Warehouse -> Many Inventory items
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Inventory> inventoryItems = new ArrayList<>();
}