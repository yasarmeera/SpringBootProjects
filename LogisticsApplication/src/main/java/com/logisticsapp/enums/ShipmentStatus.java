package com.logisticsapp.enums;

import com.logisticsapp.exception.BadRequestException;

// enum - allowed statuses of a shipment / tracking entry, with the allowed status changes
public enum ShipmentStatus {
    PENDING,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    // true if a shipment may move from this status to the next one
    public boolean canChangeTo(ShipmentStatus next) {
        if (this == next) {
            return true;
        }
        return switch (this) {
            case PENDING -> next == IN_TRANSIT || next == CANCELLED;
            case IN_TRANSIT -> next == OUT_FOR_DELIVERY || next == DELIVERED || next == CANCELLED;
            case OUT_FOR_DELIVERY -> next == DELIVERED || next == CANCELLED;
            case DELIVERED, CANCELLED -> false;
        };
    }

    // throws BadRequestException (400) if the change is not allowed
    public void validateChangeTo(ShipmentStatus next) {
        if (next != null && !canChangeTo(next)) {
            throw new BadRequestException("Cannot change shipment status from " + this + " to " + next);
        }
    }
}