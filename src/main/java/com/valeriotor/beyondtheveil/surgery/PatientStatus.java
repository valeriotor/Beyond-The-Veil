package com.valeriotor.beyondtheveil.surgery;

public class PatientStatus {

    private static int tooMuchSedativeThreshold() {
        //TODO link to server data
        return 100;
    }

    private static int enoughSedativeThreshold() {
        //TODO link to server data
        return 10;
    }


    private int sedative;

    private int softness;
    private SurgicalLocation incisedLocation = null;
    private PatientCondition condition;
    private int currentPain;

    private boolean tooSoftForSoftOrganExtraction() {
        return softness >= 10; // TODO link to server data
    }

    private boolean overTooSoftThreshold() {
        return softness >= 100; // TODO
    }

    private boolean overIncisionSoftnessThreshold() {
        return softness >= 1; // TODO
    }

    private boolean isSufficientlySedated() {
        return sedative >= enoughSedativeThreshold();
    }

    public void increaseSedative() {

    }

    private void decreaseSedative() {

    }

    public void increaseSoftness() {

    }

    private void performIncision(SurgicalLocation location) { // Slightly painful, or extremely painful if not soft enough
        incisedLocation = location;
    }

    public void tick(boolean clientSide) {

    }

    public void extract() {

    }

    /** Tickwise function, used while e.g. slicing incision
     *  Can be used client side to animate the patient if not sufficiently sedated (i.e. in pain and thrashing about)
     *  Can be used server side to decrease sedation level if performing high pain operation
     */
    public void performingPainfulOperation(PainLevel painLevel) {

    }

    public boolean hasString(String s) {
        return false;
    }

    public void setCondition(PatientCondition condition) {
        this.condition = condition;
    }

    public PatientCondition getCondition() {
        return condition;
    }

    public void setCurrentPain(int value) {
        this.currentPain = value;
    }

    // TODO Once currentLevel reaches 33/100 set sedative amount to 0 (unless condition is ASLEEP_FOREVER)

}
