package com.valeriotor.beyondtheveil.surgery.notes;

import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;

public enum ReportLocationType {
    NONE(null), BACK(SurgicalLocation.BACK), CHEST(SurgicalLocation.CHEST), SKULL(SurgicalLocation.SKULL);

    private final SurgicalLocation location;

    ReportLocationType(SurgicalLocation location) {
        this.location = location;
    }

    public SurgicalLocation getLocation() {
        return location;
    }
}
