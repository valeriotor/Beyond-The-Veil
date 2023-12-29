package com.valeriotor.beyondtheveil.surgery;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Operation {

    // create injection operation: location, fluid, amount required for completion
    // create extraction operation: location, item, additional requirements to be considered
    // create insertion operation: location, item
    private final String name;
    private final Function<PatientStatus, PainLevel> painLevel;
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

    public Operation(String name, Function<PatientStatus, PainLevel> painLevel, Predicate<PatientStatus> requirementForSuccessfulCompletion,
                     Function<PatientStatus, String> completionMessage, Consumer<PatientStatus> statusChangeOnSuccess, PatientCondition conditionIfFailed,
                     Function<PatientStatus, LivingEntity> entityChange, Set<SurgicalLocation> allowedLocations,
                     int capacityRequirement, int maximumTimesAllowed, boolean persistent, boolean eraseFluid) {
        this.name = name;
        this.painLevel = painLevel;
        this.requirementForSuccessfulCompletion = requirementForSuccessfulCompletion;
        this.completionMessage = completionMessage;
        this.statusChangeOnSuccess = statusChangeOnSuccess;
        this.conditionIfFailed = conditionIfFailed;
        this.entityChange = entityChange;
        this.allowedLocations = Collections.unmodifiableSet(allowedLocations);
        this.capacityRequirement = capacityRequirement;
        this.maximumTimesAllowed = maximumTimesAllowed;
        this.persistent = persistent;
        this.eraseFluid = eraseFluid;
    }

    public String getName() {
        return name;
    }

    public Function<PatientStatus, PainLevel> getPainLevel() {
        return painLevel;
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

    public static class Builder {
        private final String name;
        private Function<PatientStatus, PainLevel> painLevel = s -> PainLevel.NEGLIGIBLE;
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

        public Builder(String name) {
            this.name = name;
        }

        public Builder setPainLevel(PainLevel painLevel) {
            this.painLevel = s -> painLevel;
            return this;
        }

        public Builder setPainLevel(Function<PatientStatus, PainLevel> painLevel) {
            this.painLevel = painLevel;
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

        /** For injection operations, should a successful completion set the fluid's current amount to 0?
         *  For example, this is useful for repeating an operation multiple times, like coagulation.
         *
         */
        public Builder setEraseFluid(boolean eraseFluid) {
            this.eraseFluid = eraseFluid;
            return this;
        }

        private Operation buildOperation() {
            return new Operation(name, painLevel, requirementForSuccessfulCompletion, completionMessage, statusChangeOnSuccess, conditionIfFailed, entityChange, allowedLocations, capacityRequirement, maximumTimesAllowed, persistent, eraseFluid);
        }

        public Operation buildInjectionOperation(Map<Fluid, List<OperationRegistry.InjectionEntry>> registry, Fluid fluid, int amount) {
            Operation op = buildOperation();
            registry.computeIfAbsent(fluid, k -> new ArrayList<>()).add(new OperationRegistry.InjectionEntry(op, fluid, amount));
            registry.get(fluid).sort(Comparator.comparingInt(OperationRegistry.InjectionEntry::amount));
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


    }
}