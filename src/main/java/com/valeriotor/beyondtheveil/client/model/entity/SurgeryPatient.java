package com.valeriotor.beyondtheveil.client.model.entity;

import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;

/**
 * Entities that can be put in SurgicalBEs and be performed surgery upon. This is mostly useful client side to store
 * the data in the inheriting entity class that may then be used by the model and renderer to set up angles, spawn
 * particles and such.
 */
public interface SurgeryPatient {

    /**
     * Patient entities (i.e. ones stored in SurgicalBEs) are only stored client side. This is to mark them as such,
     * so they can be rendered correctly, not use server side data, and override normal behaviour as necessary
     */
    void markAsPatient();
    boolean isSurgeryPatient();

    /**
     * Useful to set the angles in the mob model.
     */
    void setPatientStatus(PatientStatus location);

    PatientStatus getPatientStatus();

    PatientType getPatientType();


}
