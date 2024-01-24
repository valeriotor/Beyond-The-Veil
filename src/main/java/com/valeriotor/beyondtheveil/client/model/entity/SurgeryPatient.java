package com.valeriotor.beyondtheveil.client.model.entity;

/** Entities that can be put in SurgicalBEs and be performed surgery upon
 */
public interface SurgeryPatient {

    /** Patient entities (i.e. ones stored in SurgicalBEs) are only stored client side. This is to mark them as such,
     *  so they can be rendered correctly, not use server side data, and override normal behaviour as necessary
     */
    void markAsPatient();

}
