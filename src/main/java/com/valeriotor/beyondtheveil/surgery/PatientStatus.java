package com.valeriotor.beyondtheveil.surgery;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientStatus {

    public PatientStatus() {
    }
    public PatientStatus(SurgicalLocation exposedLocation) {
        this.exposedLocation = exposedLocation;
    }

    private static int tooMuchSedativeThreshold() {
        //TODO link to server data
        return 100;
    }

    private static int enoughSedativeThreshold() {
        //TODO link to server data
        return 10;
    }


    private SurgicalLocation exposedLocation = SurgicalLocation.CHEST; // Just to be sure it's never null
    private boolean incised = false;
    private PatientCondition condition = PatientCondition.STABLE;
    private int leftoverCapacity;
    private int currentPain;
    private Map<Fluid, Integer> fluidAmounts = new HashMap<>();
    private Map<String, Integer> flags = new HashMap<>(); // Integer value is to check how many times we applied the flag


    /** Can only change position if there is currently no incision
     */
    public void setExposedLocation(SurgicalLocation location) {
        if (!incised) {
            exposedLocation = location;
        }
    }

    public boolean performIncision() { // TODO Slightly painful, or extremely painful if not soft enough
        if (incised) {
            return false;
        }
        PainLevel painLevel = flags.getOrDefault("soften", 0) > 0 ? PainLevel.MEDIUM : PainLevel.EXTREME;
        if (painLevel == PainLevel.EXTREME) {
            setCondition(PatientCondition.DEAD);
            return true;
        }
        incised = true;
        if (increasesCurrentPain(painLevel)) {
            increaseCurrentPain(40);
        }
        return true;
    }

    public boolean sewIncision() {
        if (!incised) {
            return false;
        }
        incised = false;
        return true;
    }

    // TODO maybe a patient in pain left alone for too long should start bleeding or dying
    public void tick(boolean clientSide) {

    }

    public ItemStack extract(Player player) {
        for (OperationRegistry.ExtractionEntry extractionOperation : OperationRegistry.EXTRACTION_OPERATIONS) {
            Operation operation = extractionOperation.operation();
            if (extractionOperation.additionalRequirements().test(this) && canPerformOperation(operation)) {
                if (increasesCurrentPain(operation.getPainLevel().apply(this))) {
                    increaseCurrentPain(34);
                }
                boolean success = elaborateOperation(player, operation);
                if (success) {
                    return extractionOperation.stack().copy();
                }
            }
        }
        return null;
    }

    /** @return if the operation was performed (and therefore the item was consumed).
     *  ItemStack shrinking logic should be handled upstream.
     */
    public boolean insert(Player player, Item heldInForceps) {
        List<OperationRegistry.InsertionEntry> insertionEntries = OperationRegistry.INSERTION_OPERATIONS.get(heldInForceps);
        if (insertionEntries == null || insertionEntries.isEmpty()) {
            return false;
        }
        for (OperationRegistry.InsertionEntry insertionEntry : insertionEntries) {
            Operation operation = insertionEntry.operation();
            if (canPerformOperation(operation)) {
                PainLevel painLevel = operation.getPainLevel().apply(this);
                if (increasesCurrentPain(painLevel)) {
                    increaseCurrentPain(34);
                }
                elaborateOperation(player, operation);
                return true;
            }
        }
        return false;
    }

    public boolean inject(Player player, FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        int newAmount = fluidAmounts.getOrDefault(fluid, 0) + fluidStack.getAmount();
        fluidAmounts.put(fluid, newAmount);
        List<OperationRegistry.InjectionEntry> injectionEntries = OperationRegistry.INJECTION_OPERATIONS.get(fluid);
        if (injectionEntries == null || injectionEntries.isEmpty()) {
            return false; // TODO or should we be more cruel? If we inserted some not-allowed fluid maybe patient should suffer from it
        }
        PainLevel painLevel = null;
        for (int i = 0; i < injectionEntries.size(); i++) {
            // How to determine PainLevel for injections, given that operations with different amounts may have
            // different levels? We check which range it is in, i.e. the next (applicable) operation for this fluid
            // that will be performed at this rate, and select the corresponding PainLevel (or the last applicable one
            // if we are already over the max amount).
            // Note that the operations in the registry are sorted by ascending amount
            OperationRegistry.InjectionEntry injectionEntry = injectionEntries.get(i);
            if (canPerformOperation(injectionEntry.operation())) {
                painLevel = injectionEntry.operation().getPainLevel().apply(this);
            }
            if (newAmount <= injectionEntry.amount()) {
                break;
            }
        }
        if (painLevel == null) {
            return false; // No applicable operation was found
        }
        boolean animationThreshold = false;
        if (increasesCurrentPain(painLevel)) { // Injections increase pain per tick
            animationThreshold = increaseCurrentPain(2);
        }
        boolean operationPerformed = false;
        for (OperationRegistry.InjectionEntry injectionEntry : injectionEntries) {
            if (injectionEntry.amount() == newAmount) {
                Operation operation = injectionEntry.operation();
                if (canPerformOperation(operation)) {
                    operationPerformed = true;
                    elaborateOperation(player, operation);
                    if (operation.isEraseFluid()) {
                        fluidAmounts.put(fluid, 0);
                    }
                }
            }
        }
        // TODO special case for injecting water: sync with client every 10mB (to enlarge patient head)
        return operationPerformed || animationThreshold;
    }

    /** @return whether the operation succedeed or failed
     */
    private boolean elaborateOperation(Player player, Operation operation) {
        //boolean canPerformOperation = canPerformOperation(operation); should be checked upstream
        //if (!canPerformOperation) return canPerformOperation;

        boolean success = operation.getRequirementForSuccessfulCompletion().test(this) && !condition.isTerminal();
        String completionMessage = operation.getCompletionMessage().apply(this);
        if (completionMessage != null) {
            player.sendSystemMessage(Component.translatable(completionMessage));
        }
        if (success) {
            operation.getStatusChangeOnSuccess().accept(this);
            // TODO entityChange
            flags.put(operation.getName(), flags.getOrDefault(operation.getName(), 0)+1);
            return true;
        } else {
            setCondition(operation.getConditionIfFailed());
            return false;
        }
    }

    @Nullable
    private boolean canPerformOperation(Operation operation) {
        return (!operation.isRequiresIncision() || incised) &&
                operation.getAllowedLocations().contains(exposedLocation) &&
                operation.getCapacityRequirement() <= leftoverCapacity &&
                (operation.getMaximumTimesAllowed() < 0 || operation.getMaximumTimesAllowed() > flags.getOrDefault(operation.getName(), 0));
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
        return condition != PatientCondition.ASLEEP_FOREVER && switch (painLevel) {
            case NEGLIGIBLE -> false;
            case MEDIUM -> flags.getOrDefault("sedate", 0) == 0;
            case HIGH -> true;
            case EXTREME -> true;
        };
    }

    /** @return if the pain exceeded an animation threshold
     */
    private boolean increaseCurrentPain(int amount) {
        currentPain += amount;
        if (currentPain >= 60) {
            flags.put("sedate", 0);
        }
        if (currentPain >= 80) {
            setCondition(PatientCondition.DEAD);
        }
        int[] animationThresholds = new int[]{30, 60, 70};
        for (int animationThreshold : animationThresholds) {
            if (currentPain >= animationThreshold && currentPain - amount < animationThreshold) {
                return true;
            }
        }
        return false;
    }

    void increaseLeftoverCapacity(int amount) {
        leftoverCapacity += amount;
    }

    boolean hasString(String s) {
        return false;
    }

    void setCondition(PatientCondition condition) {
        if (condition == PatientCondition.DEAD && this.condition != PatientCondition.DEAD) {
            // TODO play sound, show particles, do something
        } else if (condition == PatientCondition.BLEEDING) {
            // TODO maybe show particles?
        }
        this.condition = condition;
    }

    public PatientCondition getCondition() {
        return condition;
    }

    void setCurrentPain(int value) {
        this.currentPain = value;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("condition", condition.name());
        tag.putInt("current_pain", currentPain);
        tag.putInt("leftover_capacity", leftoverCapacity);
        tag.putString("exposed_location", exposedLocation.name());
        tag.putBoolean("incised", incised);
        CompoundTag flagTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            flagTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("flags", flagTag);
        CompoundTag fluidTag = new CompoundTag();
        for (Map.Entry<Fluid, Integer> entry : fluidAmounts.entrySet()) {
            ResourceLocation fluidKey = ForgeRegistries.FLUIDS.getKey(entry.getKey());
            if (fluidKey != null) {
                fluidTag.putInt(fluidKey.toString(), entry.getValue());
            }
        }
        tag.put("fluids", fluidTag);
        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        condition = PatientCondition.valueOf(tag.getString("condition"));
        currentPain = tag.getInt("current_pain");
        leftoverCapacity = tag.getInt("leftover_capacity");
        exposedLocation = SurgicalLocation.valueOf(tag.getString("exposed_location"));
        incised = tag.getBoolean("incised");
        CompoundTag flagTag = tag.getCompound("flags");
        for (String key : flagTag.getAllKeys()) {
            flags.put(key, flagTag.getInt(key));
        }
        CompoundTag fluidTag = tag.getCompound("fluids");
        for (String key : fluidTag.getAllKeys()) {
            Fluid f = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(key));
            fluidAmounts.put(f, fluidTag.getInt(key));
        }
    }


}
