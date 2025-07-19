package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.surgery.arsenal.*;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientStatus {

    private boolean dirty;

    private static final double[] ABSOLUTE_PAIN_THRESHOLDS = new double[]{10, 30, 50};

    private static final double[] MISSING_PAIN_THRESHOLDS = new double[]{100, 60, 30};
    private SurgicalLocation exposedLocation = SurgicalLocation.CHEST; // Just to be sure it's never null
    private boolean incised = false;
    // TODO condition is copied to Convalescent capability
    private PatientCondition condition = PatientCondition.STABLE;
    private int leftoverCapacity;
    private double currentPain;
    private int currentAbsolutePainThreshold; // from 0 to ABSOLUTE_PAIN_THRESHOLDS.length
    private int currentMissingPainThreshold; // from 0 to MISSING_PAIN_THRESHOLDS.length
    private int ticksSinceLastAddedPain;
    private boolean didFinalAnimation;
    private int countdownTicks = 0;
    private final Map<Fluid, Double> fluidAmounts = new HashMap<>();
    // TODO *persistent* flags are copied to Convalescent capability (new HashSet for persistent flags?)
    private Map<String, Integer> flags = new HashMap<>(); // Integer value is to check how many times we applied the flag
    private Map<String, Integer> persistentFlags = new HashMap<>(); // Integer value is to check how many times we applied the flag
    private ArsenalEffectType arsenalEffect;
    private int arsenalEffectAmplifier;
    private int arsenalEffectDuration;
    private BurstType burst = BurstRegistry.BASE;
    private int burstExtension;
    private DyeColor mutex;
    private TargetingType triggerType;
    private TargetingType targetType;
    private ServerLevel level;
    private BlockPos pos;
    private final PatientType patientType;
    private boolean exploded;
    private int ticksSinceLastInjection; // To slow down weeper creation

    public PatientStatus(PatientType patientType) {
        this.patientType = patientType;
    }

    public PatientType getPatientType() {
        return patientType;
    }

    public int getLeftoverCapacity() {
        return leftoverCapacity;
    }

    public ArsenalEffectType getArsenalEffect() {
        return arsenalEffect;
    }

    public int getArsenalEffectAmplifier() {
        return arsenalEffectAmplifier;
    }

    public int getArsenalEffectDuration() {
        return arsenalEffectDuration;
    }

    public BurstType getBurst() {
        return burst;
    }

    public int getBurstExtension() {
        return burstExtension;
    }

    public DyeColor getMutex() {
        return mutex;
    }

    public TargetingType getTriggerType() {
        return triggerType;
    }

    public TargetingType getTargetType() {
        return targetType;
    }

    public Map<String, Integer> getFlags() {
        return flags;
    }

    public Map<String, Integer> getPersistentFlags() {
        return persistentFlags;
    }

    public TriggerData getTriggerData() {
        TriggerData data = new TriggerData();
        data.setEffect(new ArsenalEffect(arsenalEffect, arsenalEffectAmplifier, arsenalEffectDuration, true));
        data.setBurst(new Burst(burst, burstExtension));
        data.setTriggerType(triggerType);
        data.setTargetType(targetType);
        data.setMutex(mutex);
        return data;
    }

    public void fromConvalescentNBT(CompoundTag convalescent) {
        ConvalescentData data = new ConvalescentData();
        data.loadFromNBT(convalescent);
        flags.clear();
        flags.putAll(data.getFlags());
        persistentFlags.clear();
        persistentFlags.putAll(data.getFlags());
        TriggerData triggerData = data.getTriggerData();
        if (triggerData.getEffect() != null) {
            arsenalEffect = triggerData.getEffect().getEffectType();
            arsenalEffectAmplifier = triggerData.getEffect().getAmplifier();
            arsenalEffectDuration = triggerData.getEffect().getDuration();
        }
        if (triggerData.getBurst() != null) {
            burst = triggerData.getBurst().getBurstType();
            burstExtension = triggerData.getBurst().getExtension();
        }
        mutex = triggerData.getMutex();
        setCondition(data.getCondition());
        leftoverCapacity = data.getCapacity();
    }

    public void setLevelAndCoords(ServerLevel level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

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
        if (op != null && !condition.isTerminal() && op.operation().getRequirementForSuccessfulStart().test(this)) {
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
        setDirty(true);
        return true;
    }

    // TODO maybe a patient in pain left alone for too long should start bleeding or dying
    public void tick(boolean clientSide) {
        if (!clientSide) {
            ticksSinceLastAddedPain = Math.max(0, ticksSinceLastAddedPain - 1);
            if (currentMissingPainThreshold > 0 && ticksSinceLastAddedPain == 0) {
                currentMissingPainThreshold = 0;
                setDirty(true);
            }
            countdownTicks = Math.max(0, countdownTicks - 1);
            if (!didFinalAnimation && countdownTicks == 1) {
                didFinalAnimation = true;
                setDirty(true);
            }
        }
    }

    public boolean extract(Player p, SurgicalBE be) {
        for (OperationRegistry.ExtractionEntry extractionOperation : OperationRegistry.EXTRACTION_OPERATIONS) {
            Operation operation = extractionOperation.operation();
            if (extractionOperation.additionalRequirements().test(this) && canPerformOperation(operation) ) {
                perTickActions(p, operation, be);
                if (currentPain >= operation.getPainForFailure()) {
                    setCondition(operation.getConditionIfFailed());
                }
                if(currentPain < operation.getPainForFailure() || condition == PatientCondition.DEAD) {
                    int currentDuration = DataUtil.getOrSetInteger(p, SurgeryItem.SurgeryItemType.TONGS.name(), 0, true);
                    if (currentDuration >= operation.getDuration() || condition == PatientCondition.DEAD) {
                        boolean wasAlreadyDead = condition == PatientCondition.DEAD;
                        boolean success = elaborateOperation(p, operation, be);
                        if (success && !wasAlreadyDead) {
                            ItemHandlerHelper.giveItemToPlayer(p, extractionOperation.stack().copy());
                        } else if (wasAlreadyDead) {
                            flags.put(operation.getName(), flags.getOrDefault(operation.getName(), 0) + 1);
                            if (operation.isPersistent()) {
                                persistentFlags.put(operation.getName(), persistentFlags.getOrDefault(operation.getName(), 0) + 1);
                            }
                        }
                        setDirty(true);
                        p.level().playSound(null, p.blockPosition(), BTVSounds.INCISION.get(), SoundSource.BLOCKS, 1, 1);
                    }
                }
                return condition != PatientCondition.DEAD;
            }
        }
        return false;
    }

    public boolean insert(Player p, CompoundTag tag, SurgicalBE be) {
        ItemStack heldInForceps = ItemStack.of(tag.getCompound("contained"));
        List<OperationRegistry.InsertionEntry> insertionEntries = OperationRegistry.INSERTION_OPERATIONS.get(heldInForceps.getItem());
        if (insertionEntries == null || insertionEntries.isEmpty() || !incised) {
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
                        setDirty(true);
                        tag.remove("contained");
                        p.level().playSound(null, p.blockPosition(), BTVSounds.INCISION.get(), SoundSource.BLOCKS, 1, 1);

                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean inject(Player p, FluidStack fluidStack, SurgicalBE be, IFluidHandlerItem syringe) {
        Fluid fluid = fluidStack.getFluid();
        if (fluidStack.isEmpty()) {
            return false;
        }
        double prevAmount = fluidAmounts.getOrDefault(fluid, 0D);
        double newAmount = prevAmount + fluidStack.getAmount();
        List<OperationRegistry.InjectionEntry> injectionEntries = OperationRegistry.INJECTION_OPERATIONS.get(fluid);
        if (injectionEntries == null || injectionEntries.isEmpty()) {
            return true; // TODO or should we be more cruel? If we inserted some not-allowed fluid maybe patient should suffer from it
        }
        for (OperationRegistry.InjectionEntry injectionEntry : injectionEntries) {
            Operation operation = injectionEntry.operation();
            if (canPerformOperation(operation)) {
                boolean enoughTicks = true;
                if (fluid == Fluids.WATER && exposedLocation == SurgicalLocation.SKULL) {
                    if (ticksSinceLastInjection <= 0) {
                        ticksSinceLastInjection = prevAmount > 470 ? 8 : (int) (prevAmount / 100);
                    } else {
                        ticksSinceLastInjection--;
                        enoughTicks = false;
                    }
                }
                if (enoughTicks) {
                    perTickActions(p, operation, be);
                    syringe.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    if (operation.isAdded(this)) {
                        fluidAmounts.put(fluid, newAmount);
                        if (fluid == Fluids.WATER && exposedLocation == SurgicalLocation.SKULL) {
                            if (Math.floor(newAmount / 5) > Math.floor(prevAmount / 5)) {
                                setDirty(true);
                            }
                            if (!condition.isTerminal()) {
                                int period = newAmount > 300 ? 35 : 90;
                                if (Math.floor(newAmount / period) > Math.floor(prevAmount / period) && newAmount < 450) {
                                    level.playSound(null, pos, BTVSounds.HEAD_STRETCH.get(), SoundSource.NEUTRAL, 1, 1);
                                }
                            }

                        }
                    }
                    operation.onConsume(this);
                    if (currentPain >= operation.getPainForFailure()) {
                        setCondition(operation.getConditionIfFailed());
                    } else if (newAmount > injectionEntry.amount()) {
                        setDirty(true);
                        elaborateOperation(p, operation, be);
                        if (operation.isEraseFluid()) {
                            fluidAmounts.put(fluid, 0D);
                        }
                    }

                }
                break;
            }

        }
        //level.sendParticles(BTVParticles.BLOODSPILL.get(), pos.getX() + 0.5 + 0, pos.getY() + 0.95 - 0.75, pos.getZ() + 0.5 + 0, 55, 0, 0, 0, 0.4);

        return true;
    }

    public double getWaterAmount() {
        return fluidAmounts.getOrDefault(Fluids.WATER, 0D);
    }

    /**
     * @return whether the operation succedeed or failed
     */
    private boolean elaborateOperation(Player player, Operation operation, SurgicalBE be) {
        //boolean canPerformOperation = canPerformOperation(operation); should be checked upstream
        //if (!canPerformOperation) return canPerformOperation;

        boolean success = operation.getRequirementForSuccessfulCompletion().test(this) && !condition.isTerminal() && operation.getCapacityRequirement() <= leftoverCapacity;
        String completionMessage = operation.getCompletionMessage().apply(this);
        if (completionMessage != null) {
            player.sendSystemMessage(Component.translatable(completionMessage));
        }
        if (success) {
            operation.getStatusChangeOnSuccess().accept(this);
            if (operation.getSuccessSound() != null) {
                player.level().playSound(null, player.blockPosition(), operation.getSuccessSound(), SoundSource.BLOCKS, 1, 1);
            }
            leftoverCapacity -= operation.getCapacityRequirement();
            // TODO entityChange (and setDirty?)
            flags.put(operation.getName(), flags.getOrDefault(operation.getName(), 0) + 1);
            updateTriggerData(operation);
            for (String playerDatum : operation.getPlayerData()) {
                DataUtil.setBooleanOnServerAndSync(player, playerDatum, true, false);
            }
            if (operation.isPersistent()) {
                persistentFlags.put(operation.getName(), persistentFlags.getOrDefault(operation.getName(), 0) + 1);
            }
            if (operation.isSuccessParticles()) {
                //level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 1.5, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));

                BlockPos blockPos = be.getBlockPos();
                Direction rotation = be.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
                Vec3 offset = operation.getParticleOffset().yRot((float) ((-rotation.get2DDataValue() - 1) * Math.PI / 2));
                ((ServerLevel) player.level()).sendParticles(operation.getSuccessParticleType(), blockPos.getX() + 0.5 + offset.x, blockPos.getY() + 1.2 + offset.y, blockPos.getZ() + 0.5 + offset.z, operation.getSuccessParticleCount(), 0, 0, 0, 1);
            }
            return true;
        } else {
            setCondition(operation.getConditionIfFailed());
            return false;
        }
    }

    private void updateTriggerData(Operation operation) {
        if (operation.getArsenalEffect() != null) {
            this.arsenalEffect = operation.getArsenalEffect();
        }
        if (operation.getBurst() != null) {
            this.burst = operation.getBurst();
        }
        if (operation.getTargetType() != null) {
            this.targetType = operation.getTargetType();
        }
        if (operation.getTriggerType() != null) {
            this.triggerType = operation.getTriggerType();
        }
        arsenalEffectAmplifier += operation.getIncreaseArsenalEffectAmplifier() ? 1 : 0;
        arsenalEffectDuration += operation.getIncreaseArsenalEffectDuration() ? 1 : 0;
        burstExtension += operation.getIncreaseBurstExtension() ? 1 : 0;
    }

    @Nullable
    private boolean canPerformOperation(Operation operation) {
        return (!operation.isRequiresIncision() || incised) &&
                operation.getAllowedLocations().contains(exposedLocation) &&
                //operation.getCapacityRequirement() <= leftoverCapacity &&
                (operation.getMaximumTimesAllowed() < 0 || operation.getMaximumTimesAllowed() > flags.getOrDefault(operation.getName(), 0)) &&
                operation.getRequirementForSuccessfulStart().test(this);
    }

    private void perTickActions(Player player, Operation operation, SurgicalBE be) {
        increaseCurrentPain(operation.getPainPerTick().applyAsDouble(this), operation.getPainForFailure(), be);
        if (operation.isProgressParticles()) {
            BlockPos blockPos = be.getBlockPos();
            Direction rotation = be.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            Vec3 base = operation.getParticleOffset();
            Vec3 offset = base.yRot((float) ((-rotation.get2DDataValue() - 1) * Math.PI / 2));
            ((ServerLevel) player.level()).sendParticles(BTVParticles.BLOODSPILL.get(), blockPos.getX() + 0.5 + offset.x, blockPos.getY() + 1.2 + offset.y, blockPos.getZ() + 0.5 + offset.z, 1, 0, 0, 0, 0.5);
        }
    }

    private void increaseCurrentPain(double amount, double amountForFailure, SurgicalBE be) {
        // TODO maybe the amount of pain just added should also affect the animation type
        // TODO or the ticks remaining until death at this rate
        //  or maximum(ticksRemaining, currentPain)
        //  Mark dirty when the current amount of added pain differs from the last tick one
        //  Check in tickServer if pain wasn't increased in the last few ticks and if so mark dirty again
        if (patientType == PatientType.WEEPER) {
            return;
        }
        if (be.hasFlebo()) {
            amount = amount * 7 / 10;
        }

        double sedativeAmount = fluidAmounts.getOrDefault(BTVFluids.SOURCE_FLUID_SEDATIVE.get(), 0D);
        double leftoverSedative = Math.max(0, sedativeAmount - amount);
        amount -= (sedativeAmount - leftoverSedative);
        fluidAmounts.put(BTVFluids.SOURCE_FLUID_SEDATIVE.get(), leftoverSedative);
        if (amount <= 0) {
            return;
        }
        currentPain += amount;
        ticksSinceLastAddedPain = 3;
        //if (currentPain >= 60) {
        //    flags.put("sedate", 0);
        //}
        updateAbsolutePainThreshold();

        double ticksTillFailure = (amountForFailure - currentPain) / amount;
        int missing = MISSING_PAIN_THRESHOLDS.length;
        for (int i = 0; i < MISSING_PAIN_THRESHOLDS.length; i++) {
            if (ticksTillFailure > MISSING_PAIN_THRESHOLDS[i]) {
                missing = i;
                break;
            }
        }

        if (missing != currentMissingPainThreshold) {
            if (missing > currentMissingPainThreshold && level != null && !condition.isTerminal()) {
                level.playSound(null, pos, SoundEvents.VILLAGER_HURT, SoundSource.NEUTRAL, 1, 1);
            }
            currentMissingPainThreshold = missing;
            setDirty(true);
        }
    }

    void decreasePain(double amount) {
        currentPain = Math.max(currentPain - amount, 0);
        updateAbsolutePainThreshold();
    }

    private void updateAbsolutePainThreshold() {
        int absolute = ABSOLUTE_PAIN_THRESHOLDS.length;
        for (int i = 0; i < ABSOLUTE_PAIN_THRESHOLDS.length; i++) {
            if (currentPain < ABSOLUTE_PAIN_THRESHOLDS[i]) {
                absolute = i;
                break;
            }
        }
        if (absolute != currentAbsolutePainThreshold) {
            currentAbsolutePainThreshold = absolute;
            setDirty(true);
        }
    }

    void increaseLeftoverCapacity(int amount) {
        leftoverCapacity += amount;
    }

    void explode() {
        if (!condition.isTerminal()) {
            level.playSound(null, pos, BTVSounds.HEAD_EXPLODE.get(), SoundSource.NEUTRAL, 1, 1);
            Direction rotation = level.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING);
            //for (int i = 0; i < 30; i++) {
            //    for (int j = 0; j < 7; j++) {
            //        Vec3 base = new Vec3(j / 16D - 0.15, 0.85, (i / 10D) - 1.5);
            //        Vec3 offset = base.yRot((float) ((-rotation.get2DDataValue() - 1) * Math.PI / 2));
            //        level.sendParticles(BTVParticles.BLOODSPILL.get(), pos.getX() + 0.5 + offset.x, pos.getY() + offset.y, pos.getZ() + 0.5 + offset.z, 5, 0, 0, 0, 0);
//
            //    }
            //}

        }
        exploded = true;
        setDirty(true);
    }

    public boolean isExploded() {
        return exploded;
    }

    boolean hasString(String s) {
        return flags.containsKey(s);
    }

    int getString(String s) {
        return flags.getOrDefault(s, 0);
    }

    void addString(String s, boolean persistent) {
        flags.put(s, flags.getOrDefault(s, 0) + 1);
        if (persistent) {
            persistentFlags.put(s, persistentFlags.getOrDefault(s, 0) + 1);
        }
    }

    void removeString(String s) {
        flags.remove(s);
        persistentFlags.remove(s);
    }

    public void setCondition(PatientCondition condition) {
        if (condition == PatientCondition.DEAD && this.condition != PatientCondition.DEAD) {
            countdownTicks = 5;
            if (level != null) {
                level.playSound(null, pos, SoundEvents.VILLAGER_DEATH, SoundSource.NEUTRAL, 1, 1);
            }
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

    double getFluidAmount(Fluid fluid) {
        return fluidAmounts.getOrDefault(fluid, 0D);
    }

    void setFluidAmount(Fluid fluid, double amount) {
        fluidAmounts.put(fluid, Math.max(amount, 0));
    }

    public double getCurrentPain() {
        return currentPain;
    }

    public int getCurrentAbsolutePainThreshold() {
        return currentAbsolutePainThreshold;
    }

    public int getCurrentMissingPainThreshold() {
        return currentMissingPainThreshold;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("condition", condition.name());
        tag.putDouble("current_pain", currentPain);
        tag.putInt("leftover_capacity", leftoverCapacity);
        tag.putString("exposed_location", exposedLocation.name());
        tag.putBoolean("incised", incised);
        //tag.putInt("absolute_threshold", currentAbsolutePainThreshold);
        tag.putInt("missing_threshold", currentMissingPainThreshold);
        CompoundTag flagTag = new CompoundTag();
        CompoundTag persistentFlagTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : flags.entrySet()) {
            flagTag.putInt(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : persistentFlags.entrySet()) {
            persistentFlagTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("flags", flagTag);
        tag.put("persistentFlags", persistentFlagTag);
        CompoundTag fluidTag = new CompoundTag();
        for (Map.Entry<Fluid, Double> entry : fluidAmounts.entrySet()) {
            ResourceLocation fluidKey = ForgeRegistries.FLUIDS.getKey(entry.getKey());
            if (fluidKey != null) {
                fluidTag.putDouble(fluidKey.toString(), entry.getValue());
            }
        }
        tag.put("fluids", fluidTag);
        tag.putBoolean("didFinalAnimation", didFinalAnimation);

        if (arsenalEffect != null) {
            tag.putString("arsenalEffect", arsenalEffect.getName());
        }
        if (burst != null) {
            tag.putString("burst", burst.getName());
        }
        tag.putInt("arsenalEffectAmplifier", arsenalEffectAmplifier);
        tag.putInt("arsenalEffectDuration", arsenalEffectDuration);
        tag.putInt("burstExtension", burstExtension);
        if (mutex != null) {
            tag.putInt("mutex", mutex.getId());
        }
        if (triggerType != null) {
            tag.putString("triggerType", triggerType.name());
        }
        if (targetType != null) {
            tag.putString("targetType", targetType.name());
        }

        return tag;
    }

    public void loadFromNBT(CompoundTag tag) {
        condition = PatientCondition.valueOf(tag.getString("condition"));
        currentPain = tag.getDouble("current_pain");
        leftoverCapacity = tag.getInt("leftover_capacity");
        exposedLocation = SurgicalLocation.valueOf(tag.getString("exposed_location"));
        incised = tag.getBoolean("incised");
        currentMissingPainThreshold = tag.getInt("missing_threshold"); // needs to be synced to client, even if we don't care about persistence
        updateAbsolutePainThreshold();
        CompoundTag flagTag = tag.getCompound("flags");
        for (String key : flagTag.getAllKeys()) {
            flags.put(key, flagTag.getInt(key));
        }
        CompoundTag persistentFlagTag = tag.getCompound("persistentFlags");
        for (String key : persistentFlagTag.getAllKeys()) {
            persistentFlags.put(key, persistentFlagTag.getInt(key));
        }
        CompoundTag fluidTag = tag.getCompound("fluids");
        for (String key : fluidTag.getAllKeys()) {
            Fluid f = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(key));
            fluidAmounts.put(f, fluidTag.getDouble(key));
        }
        if(tag.contains("arsenalEffect")) {
            arsenalEffect = ArsenalEffectRegistry.byName(tag.getString("arsenalEffect"));
        }
        if (tag.contains("arsenalEffectAmplifier")) {
            arsenalEffectAmplifier = tag.getInt("arsenalEffectAmplifier");
        }
        if (tag.contains("arsenalEffectDuration")) {
            arsenalEffectDuration = tag.getInt("arsenalEffectDuration");
        }
        if(tag.contains("burst")) {
            burst = BurstRegistry.byName(tag.getString("burst"));
        }
        if (tag.contains("burstExtension")) {
            burstExtension = tag.getInt("burstExtension");
        }
        if(tag.contains("mutex")) {
            mutex = DyeColor.byId(tag.getInt("mutex"));
        }
        if(tag.contains("triggerType")) {
            triggerType = TargetingType.valueOf(tag.getString("triggerType"));
        }
        if(tag.contains("targetType")) {
            targetType = TargetingType.valueOf(tag.getString("targetType"));
        }
        didFinalAnimation = tag.getBoolean("didFinalAnimation");
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }

    public boolean isDead() {
        return condition == PatientCondition.DEAD;
    }

    public boolean didFinalAnimation() {
        return didFinalAnimation;
    }
}
