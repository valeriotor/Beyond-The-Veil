package com.valeriotor.beyondtheveil.surgery.notes;

import com.valeriotor.beyondtheveil.surgery.PatientType;

import java.util.Set;

public enum ReportPatientType {
    HUMAN(Set.of(PatientType.VILLAGER, PatientType.PLAYER)), WEEPER(Set.of(PatientType.WEEPER));

    private final Set<PatientType> types;

    ReportPatientType(Set<PatientType> types) {
        this.types = types;
    }

    public Set<PatientType> getTypes() {
        return types;
    }
}
