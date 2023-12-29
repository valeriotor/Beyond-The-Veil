package com.valeriotor.beyondtheveil.surgery;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.*;

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
    private int leftoverCapacity;
    private int currentPain;
    private Map<Fluid, Integer> fluidAmounts = new HashMap<>();
    private Map<String, Integer> flags = new HashMap<>(); // Integer value is to check how many times we applied the flag

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

    // TODO maybe a patient in pain left alone for too long should start bleeding or dying
    public void tick(boolean clientSide) {

    }

    public ItemStack extract(Player player) {
        for (OperationRegistry.ExtractionEntry extractionOperation : OperationRegistry.EXTRACTION_OPERATIONS) {
            Operation operation = extractionOperation.operation();
            if (operation.getAllowedLocations().contains(incisedLocation) && extractionOperation.additionalRequirements().test(this)) {
                Tuple<Boolean, Boolean> result = elaborateOperation(player, operation);
                if (result.getA() && increasesCurrentPain(operation.getPainLevel().apply(this))) {
                    increaseCurrentPain(34);
                }
                if (result.getA() && result.getB()) {
                    return extractionOperation.stack().copy();
                }
            }
        }
        return null;
    }

    /** @return if the item was consumed. ItemStack shrinking logic should be handled upstream.
     */
    public boolean insert(Player player, InteractionHand interactionHand, ItemStack heldInForceps) {
        List<OperationRegistry.InsertionEntry> insertionEntries = OperationRegistry.INSERTION_OPERATIONS.get(heldInForceps.getItem());
        if (insertionEntries == null || insertionEntries.isEmpty()) {
            return false;
        }
        PainLevel painLevel = null;
        for (OperationRegistry.InsertionEntry insertionEntry : insertionEntries) {
            if (insertionEntry.operation().getAllowedLocations().contains(incisedLocation)) {
                painLevel = insertionEntry.operation().getPainLevel().apply(this);
            }
        }
        if (painLevel == null) { // No applicable location was found
            return false;
        }
        for (OperationRegistry.InsertionEntry insertionEntry : insertionEntries) {
            Operation operation = insertionEntry.operation();

            Tuple<Boolean, Boolean> result = elaborateOperation(player, operation);
            if (result.getA()) {
                if (increasesCurrentPain(painLevel)) {
                    increaseCurrentPain(34);
                }
            }
            return result.getA();
        }
        return false;
    }

    public void inject(Player player, InteractionHand interactionHand, Fluid fluid) {
        int newAmount = fluidAmounts.getOrDefault(fluid, 0) + 1;
        fluidAmounts.put(fluid, newAmount);
        List<OperationRegistry.InjectionEntry> injectionEntries = OperationRegistry.INJECTION_OPERATIONS.get(fluid);
        if (injectionEntries == null || injectionEntries.isEmpty()) {
            return; // TODO or should we be more cruel? If we inserted some not-allowed fluid maybe patient should suffer from it
        }
        PainLevel painLevel = null;
        for (int i = 0; i < injectionEntries.size(); i++) {
            // How to determine PainLevel for injections, given that operations with different amounts may have
            // different levels? We check which range it is in, i.e. the next (applicable) operation for this fluid
            // that will be performed at this rate, and select the corresponding PainLevel (or the last applicable one
            // if we are already over the max amount).
            // Note that the operations in the registry are sorted by ascending amount
            OperationRegistry.InjectionEntry injectionEntry = injectionEntries.get(i);
            if (injectionEntry.operation().getAllowedLocations().contains(incisedLocation)) {
                painLevel = injectionEntry.operation().getPainLevel().apply(this);
            }
            if (newAmount <= injectionEntry.amount()) {
                break;
            }
        }
        if (painLevel == null) {
            return; // No applicable (i.e. for this surgical location) operation was found
        }

        for (OperationRegistry.InjectionEntry injectionEntry : injectionEntries) {
            if (injectionEntry.amount() == newAmount) {
                Operation operation = injectionEntry.operation();
                Tuple<Boolean, Boolean> result = elaborateOperation(player, operation);
                if (result.getA()) {
                    if (operation.isEraseFluid()) {
                        fluidAmounts.put(fluid, 0);
                    }
                }
            }
        }
        if (increasesCurrentPain(painLevel)) { // Injections increase pain per tick
            increaseCurrentPain(2);
        }
    }

    /** @return A tuple of two values:
     *  Value A is whether the operation *was performed or not*. This return value may be used, for example, to see
     *  whether the item was consumed during an insertion operation.
     *  Value B is whether the operation succedeed or failed (both of which may only happen if the operation was performed).
     */
    private Tuple<Boolean, Boolean> elaborateOperation(Player player, Operation operation) {
        if (!operation.getAllowedLocations().contains(incisedLocation) ||
            operation.getCapacityRequirement() > leftoverCapacity ||
            (operation.getMaximumTimesAllowed() >= 0 && operation.getMaximumTimesAllowed() <= flags.getOrDefault(operation.getName(), 0))) {
            return new Tuple<>(false, false);
        }

        boolean success = operation.getRequirementForSuccessfulCompletion().test(this);
        String completionMessage = operation.getCompletionMessage().apply(this);
        if (completionMessage != null) {
            player.sendSystemMessage(Component.translatable(completionMessage));
        }
        if (success) {
            operation.getStatusChangeOnSuccess().accept(this);
            // TODO entityChange
            flags.put(operation.getName(), flags.getOrDefault(operation.getName(), 0)+1);
            return new Tuple<>(true, true);
        } else {
            setCondition(operation.getConditionIfFailed());
            return new Tuple<>(true, false);
        }
    }

    private boolean wasTooPainfulToSucceed(PainLevel painLevel) {
        return switch (painLevel) {
            case NEGLIGIBLE -> false;
            case MEDIUM -> flags.getOrDefault("sedate", 0) > 0;
            case HIGH -> flags.getOrDefault("sedate", 0) > 0;
            case EXTREME -> true;
        };
    }

    private boolean increasesCurrentPain(PainLevel painLevel) {
        return switch (painLevel) {
            case NEGLIGIBLE -> false;
            case MEDIUM -> flags.getOrDefault("sedate", 0) == 0;
            case HIGH -> true;
            case EXTREME -> true;
        };
    }

    private void increaseCurrentPain(int amount) {
        currentPain += amount;
        if (currentPain >= 60) {
            flags.put("sedate", 0);
        }
        if (currentPain >= 80) {
            setCondition(PatientCondition.DEAD);
        }
    }

    /**
     * Tickwise function, used while e.g. slicing incision
     * Can be used client side to animate the patient if not sufficiently sedated (i.e. in pain and thrashing about)
     * Can be used server side to decrease sedation level if performing high pain operation
     */
    public void performingPainfulOperation(PainLevel painLevel) {

    }

    public boolean hasString(String s) {
        return false;
    }

    public void setCondition(PatientCondition condition) {
        this.condition = condition;
        if (condition == PatientCondition.DEAD) {
            // TODO play sound, show particles, do something
        } else if (condition == PatientCondition.BLEEDING) {
            // TODO maybe show particles?
        }
    }

    public PatientCondition getCondition() {
        return condition;
    }

    public void setCurrentPain(int value) {
        this.currentPain = value;
    }

    // TODO Once currentLevel reaches 33/100 set sedative amount to 0 (unless condition is ASLEEP_FOREVER)

}
