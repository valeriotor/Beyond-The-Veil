package com.valeriotor.beyondtheveil.surgery;

import com.google.common.collect.Lists;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class Operation {

    // create injection operation: location, fluid, amount required for completion
    // create extraction operation: location, item, additional requirements to be considered
    // create insertion operation: location, item
    private final String name;
    private final Predicate<PatientStatus> requirementForSuccessfulCompletion;
    private final Function<PatientStatus, String> completionMessage;
    private final Consumer<PatientStatus> statusChangeOnSuccess;
    private final PatientCondition conditionIfFailed;
    private final Function<PatientStatus, LivingEntity> entityChange;
    private final Set<SurgicalLocation> allowedLocations;
    private final int capacityRequirement;
    private final int maximumTimesAllowed;
    private final boolean persistent;
    private final boolean eraseFluid;
    private final boolean requiresIncision;
    private final int duration;
    private final ToDoubleFunction<PatientStatus> painPerTick;
    private final double painForFailure;
    private final Consumer<PatientStatus> onConsume;
    private final Predicate<PatientStatus> isAdded;
    private final boolean progressParticles;
    private final boolean successParticles;
    private final boolean failureParticles;
    private final Vec3 particleOffset;

    public Operation(Builder b) {
        this.name = b.name;
        this.requirementForSuccessfulCompletion = b.requirementForSuccessfulCompletion;
        this.completionMessage = b.completionMessage;
        this.statusChangeOnSuccess = b.statusChangeOnSuccess;
        this.conditionIfFailed = b.conditionIfFailed;
        this.entityChange = b.entityChange;
        this.allowedLocations = Collections.unmodifiableSet(b.allowedLocations);
        this.capacityRequirement = b.capacityRequirement;
        this.maximumTimesAllowed = b.maximumTimesAllowed;
        this.persistent = b.persistent;
        this.eraseFluid = b.eraseFluid;
        this.requiresIncision = b.requiresIncision;
        this.duration = b.duration;
        this.painPerTick = b.painPerTick;
        this.painForFailure = b.painForFailure;
        this.onConsume = b.onConsume;
        this.isAdded = b.isAdded;
        this.progressParticles = b.progressParticles;
        this.successParticles = b.successParticles;
        this.failureParticles = b.failureParticles;
        this.particleOffset = b.particleOffset;
    }

    public String getName() {
        return name;
    }

    public Predicate<PatientStatus> getRequirementForSuccessfulCompletion() {
        return requirementForSuccessfulCompletion;
    }

    public Function<PatientStatus, String> getCompletionMessage() {
        return completionMessage;
    }

    public Consumer<PatientStatus> getStatusChangeOnSuccess() {
        return statusChangeOnSuccess;
    }

    public PatientCondition getConditionIfFailed() {
        return conditionIfFailed;
    }

    public Function<PatientStatus, LivingEntity> getEntityChange() {
        return entityChange;
    }

    public Set<SurgicalLocation> getAllowedLocations() {
        return allowedLocations;
    }

    public int getCapacityRequirement() {
        return capacityRequirement;
    }

    public int getMaximumTimesAllowed() {
        return maximumTimesAllowed;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean isEraseFluid() {
        return eraseFluid;
    }

    public boolean isRequiresIncision() {
        return requiresIncision;
    }

    public int getDuration() {
        return duration;
    }

    public ToDoubleFunction<PatientStatus> getPainPerTick() {
        return painPerTick;
    }

    public double getPainForFailure() {
        return painForFailure;
    }

    public void onConsume(PatientStatus status) {
        onConsume.accept(status);
    }

    public boolean isAdded(PatientStatus status) {
        return isAdded.test(status);
    }

    public boolean isProgressParticles() {
        return progressParticles;
    }

    public boolean isSuccessParticles() {
        return successParticles;
    }

    public boolean isFailureParticles() {
        return failureParticles;
    }

    public Vec3 getParticleOffset() {
        return particleOffset;
    }

    public static class Builder {
        private final String name;
        private Predicate<PatientStatus> requirementForSuccessfulCompletion = s -> true; // e.g. too much softener made the heart unusable
        private Function<PatientStatus, String> completionMessage = s -> null; // translation key
        private Consumer<PatientStatus> statusChangeOnSuccess = s -> {
        }; // Any particular effects, e.g. removing a spine turns the PatientCondition into BLEEDING
        private PatientCondition conditionIfFailed = PatientCondition.DEAD;
        private Function<PatientStatus, LivingEntity> entityChange = null;
        private Set<SurgicalLocation> allowedLocations = EnumSet.noneOf(SurgicalLocation.class);
        private int capacityRequirement = 0;
        private int maximumTimesAllowed = 1; // if value > 1 add a number at the end of the name when adding it to patient state
        private boolean persistent = false; // does data stay after removing patient from bed/cradle?
        private boolean eraseFluid = false;
        private boolean updateClientOnSuccess = false; // TODO include these if necessary
        private boolean updateClientOnFailure = false;
        private boolean requiresIncision = false;
        private int duration;
        private ToDoubleFunction<PatientStatus> painPerTick = s -> 0;
        private double painForFailure = Integer.MAX_VALUE;
        private Consumer<PatientStatus> onConsume = s -> {
        };
        private Predicate<PatientStatus> isAdded = s -> true;
        private boolean progressParticles = false;
        private boolean successParticles = false;
        private boolean failureParticles = false;
        private Vec3 particleOffset = Vec3.ZERO;

        public Builder(String name) {
            this.name = name;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setPainPerTick(double painPerTick) {
            return setPainPerTick(s -> painPerTick);
        }

        public Builder setPainPerTick(ToDoubleFunction<PatientStatus> painPerTick) {
            this.painPerTick = painPerTick;
            return this;
        }

        /**
         * At what pain level is the operation guaranteed to fail
         */
        public Builder setPainForFailure(double painForFailure) { // because we're making it deterministic I guess?
            this.painForFailure = painForFailure;
            return this;
        }

        /**
         * Used in injections and insertions, respectively when consuming 1mB of fluid or the whole item
         */
        public Builder onConsume(Consumer<PatientStatus> onConsume) {
            this.onConsume = onConsume;
            return this;
        }

        /**
         * Used in injections and insertions, to determine whether the mB or the item is added to the PatientStatus
         * after consumption
         */
        public Builder isAdded(Predicate<PatientStatus> isAdded) {
            this.isAdded = isAdded;
            return this;
        }

        public Builder setRequirementForSuccessfulCompletion(Predicate<PatientStatus> requirementForSuccessfulCompletion) {
            this.requirementForSuccessfulCompletion = requirementForSuccessfulCompletion;
            return this;
        }


        /**
         * Used, for example, to send failure messages when the conditions match those in the successful completion
         * predicate
         */
        public Builder setCompletionMessage(Function<PatientStatus, String> completionMessage) {
            this.completionMessage = completionMessage;
            return this;
        }

        public Builder setStatusChangeOnSuccess(Consumer<PatientStatus> statusChangeOnSuccess) {
            this.statusChangeOnSuccess = statusChangeOnSuccess;
            return this;
        }

        public Builder setConditionIfFailed(PatientCondition conditionIfFailed) {
            this.conditionIfFailed = conditionIfFailed;
            return this;
        }

        public Builder setEntityChange(Function<PatientStatus, LivingEntity> entityChange) {
            this.entityChange = entityChange;
            return this;
        }

        public Builder addAllowedLocation(SurgicalLocation allowedLocation) {
            this.allowedLocations.add(allowedLocation);
            return this;
        }

        public Builder allowAllLocations() {
            this.allowedLocations.addAll(List.of(SurgicalLocation.values()));
            return this;
        }

        public Builder setCapacityRequirement(int capacityRequirement) {
            this.capacityRequirement = capacityRequirement;
            return this;
        }

        /**
         * How many times can this operation be repeated? Default is 1.
         * If the value is set to -1 it can be repeated any number of times.
         * For injectionOperations
         */
        public Builder setMaximumTimesAllowed(int maximumTimesAllowed) { // TODO make PatientStatus dependent version
            this.maximumTimesAllowed = maximumTimesAllowed;
            return this;
        }

        public Builder setPersistent(boolean persistent) {
            this.persistent = persistent;
            return this;
        }

        /**
         * For injection operations, should a successful completion set the fluid's current amount to 0?
         * For example, this is useful for repeating an operation multiple times, like coagulation.
         */
        public Builder setEraseFluid(boolean eraseFluid) {
            this.eraseFluid = eraseFluid;
            return this;
        }

        public Builder setRequiresIncision(boolean requiresIncision) {
            this.requiresIncision = requiresIncision;
            return this;
        }

        public Builder setProgressParticles(boolean progressParticles) {
            this.progressParticles = progressParticles;
            return this;
        }

        public Builder setSuccessParticles(boolean successParticles) {
            this.successParticles = successParticles;
            return this;
        }

        public Builder setParticleOffset(Vec3 particleOffset) {
            this.particleOffset = particleOffset;
            return this;
        }

        //public Builder setFailureParticles(boolean failureParticles) {
            //this.failureParticles = failureParticles;
            //return this;
        //}

        private Operation buildOperation() {
            return new Operation(this);
        }

        public Operation buildInjectionOperation(Fluid fluid, int amount) {
            Operation op = buildOperation();
            OperationRegistry.INJECTION_OPERATIONS.computeIfAbsent(fluid, k -> new ArrayList<>()).add(new OperationRegistry.InjectionEntry(op, fluid, amount));
            OperationRegistry.INJECTION_OPERATIONS.get(fluid).sort(Comparator.comparingInt(OperationRegistry.InjectionEntry::amount));
            OperationRegistry.OPERATIONS_BY_NAME.put(op.getName(), op);
            return op;
        }

        public Operation buildExtractionOperation(List<OperationRegistry.ExtractionEntry> registry, ItemStack stack) {
            return buildExtractionOperation(registry, stack, s -> true);
        }

        public Operation buildExtractionOperation(List<OperationRegistry.ExtractionEntry> registry, ItemStack stack, Predicate<PatientStatus> additionalRequirements) {
            Operation op = buildOperation();
            registry.add(new OperationRegistry.ExtractionEntry(op, stack.copy(), additionalRequirements));
            OperationRegistry.OPERATIONS_BY_NAME.put(op.getName(), op);
            return op;
        }

        public Operation buildInsertionOperation(Map<Item, List<OperationRegistry.InsertionEntry>> registry, Item item) {
            Operation op = buildOperation();
            registry.computeIfAbsent(item, k -> new ArrayList<>()).add(new OperationRegistry.InsertionEntry(op, item));
            OperationRegistry.OPERATIONS_BY_NAME.put(op.getName(), op);
            return op;
        }

        public Operation buildIncisionOperation(SurgicalLocation location) { // may one day be more specific surgical locations
            Operation op = buildOperation();
            OperationRegistry.INCISION_OPERATIONS.put(location, new OperationRegistry.IncisionEntry(op, location));
            OperationRegistry.OPERATIONS_BY_NAME.put(op.getName(), op);
            return op;
        }

    }
}