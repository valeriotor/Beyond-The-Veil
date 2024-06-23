package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientStatus {

    private boolean dirty;

    public PatientStatus() {
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
    private double currentPain;
    private final Map<Fluid, Double> fluidAmounts = new HashMap<>();
    private Map<String, Integer> flags = new HashMap<>(); // Integer value is to check how many times we applied the flag


    /**
     * Can only change position if there is currently no incision
     */
    public void setExposedLocation(SurgicalLocation location) {
        if (!incised) {
            exposedLocation = location;
        }
    }

    public boolean isIncised() {
        return incised;
    }

    public SurgicalLocation getExposedLocation() {
        return exposedLocation;
    }

    public boolean performIncision(Player p, SurgicalBE be) { // TODO Slightly painful, or extremely painful if not soft enough
        if (incised) {
            return false;
        }
        OperationRegistry.IncisionEntry op = OperationRegistry.INCISION_OPERATIONS.get(this.exposedLocation);
        if (op != null && !condition.isTerminal()) {
            Operation operation = op.operation();
            perTickActions(p, operation, be);
            if (currentPain >= operation.getPainForFailure()) {
                setCondition(operation.getConditionIfFailed());
            } else {
                int currentDuration = DataUtil.getOrSetInteger(p, SurgeryItem.SurgeryItemType.SCALPEL.name(), 0, true);
                if (currentDuration >= operation.getDuration()) {
                    incised = elaborateOperation(p, operation, be);
                    // TODO slight move head to one side animation?
                    setDirty(true);
                    p.level().playSound(null, p.blockPosition(), BTVSounds.INCISION.get(), SoundSource.BLOCKS, 1, 1);
                }
            }
            return true;
        }
        return false;
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

    public boolean extract(Player p, SurgicalBE be) {
        for (OperationRegistry.ExtractionEntry extractionOperation : OperationRegistry.EXTRACTION_OPERATIONS) {
            Operation operation = extractionOperation.operation();
            if (extractionOperation.additionalRequirements().test(this) && canPerformOperation(operation)) {
                perTickActions(p, operation, be);
                if (currentPain >= operation.getPainForFailure()) {
                    setCondition(operation.getConditionIfFailed());
                } else {
                    int currentDuration = DataUtil.getOrSetInteger(p, SurgeryItem.SurgeryItemType.TONGS.name(), 0, true);
                    if (currentDuration >= operation.getDuration()) {
                        boolean success = elaborateOperation(p, operation, be);
                        if (success) {
                            setDirty(true);
                            ItemHandlerHelper.giveItemToPlayer(p, extractionOperation.stack().copy());
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @return if the operation was performed (and therefore the item was consumed).
     * ItemStack shrinking logic should be handled upstream.
     */
    public boolean insert(Player p, Item heldInForceps, SurgicalBE be) {
        List<OperationRegistry.InsertionEntry> insertionEntries = OperationRegistry.INSERTION_OPERATIONS.get(heldInForceps);
        if (insertionEntries == null || insertionEntries.isEmpty()) {
            return false;
        }
        for (OperationRegistry.InsertionEntry insertionEntry : insertionEntries) {
            Operation operation = insertionEntry.operation();
            if (canPerformOperation(operation)) {
                perTickActions(p, operation, be);
                if (currentPain >= operation.getPainForFailure()) {
                    setCondition(operation.getConditionIfFailed());
                } else {
                    int currentDuration = DataUtil.getOrSetInteger(p, SurgeryItem.SurgeryItemType.FORCEPS.name(), 0, true);
                    if (currentDuration >= operation.getDuration()) {
                        boolean success = elaborateOperation(p, operation, be);
                        if (success) {
                            setDirty(true);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean inject(Player p, FluidStack fluidStack, SurgicalBE be) {
        Fluid fluid = fluidStack.getFluid();
        if (fluidStack.isEmpty()) {
            return false;
        }
        double newAmount = fluidAmounts.getOrDefault(fluid, 0D) + fluidStack.getAmount();
        fluidAmounts.put(fluid, newAmount);
        List<OperationRegistry.InjectionEntry> injectionEntries = OperationRegistry.INJECTION_OPERATIONS.get(fluid);
        if (injectionEntries == null || injectionEntries.isEmpty()) {
            return true; // TODO or should we be more cruel? If we inserted some not-allowed fluid maybe patient should suffer from it
        }
        for (OperationRegistry.InjectionEntry injectionEntry : injectionEntries) {
            Operation operation = injectionEntry.operation();
            if (canPerformOperation(operation)) {
                perTickActions(p, operation, be);
                if (currentPain >= operation.getPainForFailure()) {
                    setCondition(operation.getConditionIfFailed());
                } else if (newAmount > injectionEntry.amount()) {
                    setDirty(true);
                    elaborateOperation(p, operation, be);
                    if (operation.isEraseFluid()) {
                        fluidAmounts.put(fluid, 0D);
                    }
                }
                break;
            }

        }
        // TODO special case for injecting water: sync with client every 10mB (to enlarge patient head)
        return true;
    }

    /**
     * @return whether the operation succedeed or failed
     */
    private boolean elaborateOperation(Player player, Operation operation, SurgicalBE be) {
        //boolean canPerformOperation = canPerformOperation(operation); should be checked upstream
        //if (!canPerformOperation) return canPerformOperation;

        boolean success = operation.getRequirementForSuccessfulCompletion().test(this) && !condition.isTerminal();
        String completionMessage = operation.getCompletionMessage().apply(this);
        if (completionMessage != null) {
            player.sendSystemMessage(Component.translatable(completionMessage));
        }
        if (success) {
            operation.getStatusChangeOnSuccess().accept(this);
            // TODO entityChange (and setDirty?)
            flags.put(operation.getName(), flags.getOrDefault(operation.getName(), 0) + 1);
            if (operation.isSuccessParticles()) {
                //level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 1.5, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));

                BlockPos blockPos = be.getBlockPos();
                ((ServerLevel) player.level()).sendParticles(BTVParticles.BLOODSPILL.get(), blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, 50, 0, 0, 0, 1);
            }
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

    private void perTickActions(Player player, Operation operation, SurgicalBE be) {
        increaseCurrentPain(operation.getPainPerTick().applyAsDouble(this));
        if (operation.isProgressParticles()) {
            BlockPos blockPos = be.getBlockPos();
            ((ServerLevel) player.level()).sendParticles(BTVParticles.BLOODSPILL.get(), blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, 1, 0, 0, 0, 0.5);
        }
    }

    /**
     * @return if the pain exceeded an animation threshold
     */
    private void increaseCurrentPain(double amount) {
        // TODO maybe the amount of pain just added should also affect the animation type
        double sedativeAmount = fluidAmounts.getOrDefault(Registration.SOURCE_FLUID_SEDATIVE.get(), 0D);
        double leftoverSedative = Math.max(0, sedativeAmount - amount);
        amount -= (sedativeAmount - leftoverSedative);
        fluidAmounts.put(Registration.SOURCE_FLUID_SEDATIVE.get(), leftoverSedative);
        if (amount <= 0) {
            return;
        }
        currentPain += amount;
        //if (currentPain >= 60) {
        //    flags.put("sedate", 0);
        //}
        int[] animationThresholds = new int[]{1, 10, 30, 50};
        for (int animationThreshold : animationThresholds) {
            if (currentPain >= animationThreshold && currentPain - amount < animationThreshold) {
                setDirty(true);
            }
        }
    }

    void increaseLeftoverCapacity(int amount) {
        leftoverCapacity += amount;
    }

    boolean hasString(String s) {
        return flags.containsKey(s);
    }

    void setCondition(PatientCondition condition) {
        if (condition == PatientCondition.DEAD && this.condition != PatientCondition.DEAD) {
            // TODO play sound, show particles, animations, something
        } else if (condition == PatientCondition.BLEEDING) {
            // TODO maybe show particles?
        }
        this.condition = condition;
        setDirty(true);
    }

    public PatientCondition getCondition() {
        return condition;
    }

    void setCurrentPain(double value) {
        this.currentPain = value;
    }

    public double getCurrentPain() {
        return currentPain;
    }

    public void decreasePain(double amount) {
        currentPain = Math.max(currentPain - amount, 0);
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("condition", condition.name());
        tag.putDouble("current_pain", currentPain);
        tag.putInt("leftover_capacity", leftoverCapacity);
        tag.putString("exposed_location", exposedLocation.name());
        tag.putBoolean("incised", incised);
        CompoundTag flagTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            flagTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("flags", flagTag);
        CompoundTag fluidTag = new CompoundTag();
        for (Map.Entry<Fluid, Double> entry : fluidAmounts.entrySet()) {
            ResourceLocation fluidKey = ForgeRegistries.FLUIDS.getKey(entry.getKey());
            if (fluidKey != null) {
                fluidTag.putDouble(fluidKey.toString(), entry.getValue());
            }
        }
        tag.put("fluids", fluidTag);
        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        condition = PatientCondition.valueOf(tag.getString("condition"));
        currentPain = tag.getDouble("current_pain");
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
            fluidAmounts.put(f, fluidTag.getDouble(key));
        }
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
}
