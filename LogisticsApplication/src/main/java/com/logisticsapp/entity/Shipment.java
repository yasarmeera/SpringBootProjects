package com.logisticsapp.entity;

import com.logisticsapp.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// entity - shipments table (belongs to one customer, has many tracking entries and one delivery)
@Entity
@Table(name = "shipments")
@Getter
@Setter
public class Shipment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.PENDING;

    private LocalDateTime shipmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // One Shipment -> Many Tracking updates
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<Tracking> trackingList = new ArrayList<>();

    // One Shipment -> One Delivery
    @OneToOne(mappedBy = "shipment", cascade = CascadeType.ALL)
    private Delivery delivery;
}