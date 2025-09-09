package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.surgery.PatientType;

public enum BloodPoolEntityType {
    ABOMINATION0,
    ABOMINATION1,
    ABOMINATION2,
    BLOOD_SKELETON,
    BLOOD_ZOMBIE,
    VILLAGER,
    WEEPER;

    public static BloodPoolEntityType fromPatientType(PatientType type) {
        return switch (type) {
            case VILLAGER -> VILLAGER;
            case WEEPER -> WEEPER;
        };
    }

}
