package com.logisticsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// entity - delivery table (one delivery belongs to one shipment)
@Entity
@Table(name = "delivery")
@Getter
@Setter
public class Delivery extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime deliveryDate;

    private String deliveredBy;

    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", unique = true)
    private Shipment shipment;
}