package com.valeriotor.beyondtheveil.surgery;

/** Used for various things:
 *  A) Position of the patient, lying on back or belly on the bed, or placed in the watery cradle
 *  B) Incision area. When incision is performed, this will be visible e.g. a message will appear under the crosshair
 *  stating that x area was incised
 *
 */
public enum SurgicalLocation {
    BACK,   // Supine, Lumbar Incision
    CHEST,  // Prone, Thoracotomy
    SKULL   // Watery Cradle, ?????

}
