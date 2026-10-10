package com.logisticsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// entity - customers table (one customer -> many shipments, audit columns come from Auditable)
@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String address;

    // One Customer -> Many Shipments
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Shipment> shipments = new ArrayList<>();
}