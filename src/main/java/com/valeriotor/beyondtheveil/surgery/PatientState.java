package com.valeriotor.beyondtheveil.surgery;

/** These should be visible to the Player, unlike "hidden" states such as "fully sedated", "soft enough".
 *  Thus these mainly include terminal conditions (e.g. "dead"), or ones that need to be taken care of
 *  immediately (e.g. "bleeding"), or shock value ones (e.g. "resurrected")
 */
public enum PatientState {
    STABLE(false),
    DEAD(true),
    ASLEEP_FOREVER(true), // In game: it is only revealed after a painful operation, as "Unresponsive: the last operation elicited no reaction".
    TOO_SOFT(true), // In game: "Mushy skin. Will not survive."
    BLEEDING(false), // In game: "Bleeding! Needs coagulant urgently!"
    RESURRECTED(false);

    private final boolean terminal;

    PatientState(boolean terminal) {
        this.terminal = terminal;
    }
}
