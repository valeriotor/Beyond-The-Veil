package com.valeriotor.beyondtheveil.surgery;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class OperationRegistry {

    public record InjectionEntry(Operation operation, Fluid fluid, int amount) {
    }

    public record ExtractionEntry(Operation operation, ItemStack stack,
                                  Predicate<PatientStatus> additionalRequirements) {
    }

    public record InsertionEntry(Operation operation, Item item) {
    }

    public record IncisionEntry(Operation operation, SurgicalLocation location) {
    }

    static final Map<String, Operation> OPERATIONS_BY_NAME = new HashMap<>();
    static final Map<Fluid, List<InjectionEntry>> INJECTION_OPERATIONS = new HashMap<>();
    static final List<ExtractionEntry> EXTRACTION_OPERATIONS = new ArrayList<>();
    static final Map<Item, List<InsertionEntry>> INSERTION_OPERATIONS = new HashMap<>();
    static final Map<SurgicalLocation, OperationRegistry.IncisionEntry> INCISION_OPERATIONS = new HashMap<>();

    private static final Operation INCISE_BACK = new Operation.Builder("incise_back")
            .setPainPerTick(0.9)
            .setDuration(120)
            .setPainForFailure(40)
            .buildIncisionOperation(SurgicalLocation.BACK);

    private static final Operation INCISE_CHEST = new Operation.Builder("incise_chest")
            .setPainPerTick(3)
            .setDuration(80)
            .setPainForFailure(150)
            .buildIncisionOperation(SurgicalLocation.CHEST);

    private static final Operation EXTRACT_HEART = new Operation.Builder("extract_heart")
            .setPainPerTick(s -> s.hasString("soft") ? 0.4 : 4)
            .setDuration(180)
            .setPainForFailure(50)
            .setRequirementForSuccessfulCompletion(s -> !s.hasString("soften_too_much"))
            .setCompletionMessage(s -> s.hasString("soften_too_much") ? "surgery.extract_heart.too_soft" : null)
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.DEAD))
            .setPersistent(true)
            .setRequiresIncision(true)
            .buildExtractionOperation(EXTRACTION_OPERATIONS, new ItemStack(Registration.HEART.get()), s -> !s.hasString("extract_heart"));

    private static final Operation EXTRACT_SPINE = new Operation.Builder("extract_spine")
            .setPainPerTick(0.4)
            .setDuration(180)
            .setPainForFailure(50)
            .addAllowedLocation(SurgicalLocation.BACK)
            // TODO .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING))
            .setPersistent(true)
            .setRequiresIncision(true)
            .buildExtractionOperation(EXTRACTION_OPERATIONS, new ItemStack(Registration.SPINE.get()), s -> !s.hasString("extract_spine"));

    private static final Operation FILL_BRAIN = new Operation.Builder("fill_brain")
            .addAllowedLocation(SurgicalLocation.SKULL)
            // TODO .setEntityChange(s -> new WeeperEntity())
            // TODO sendClientMessage(explode head animation)
            // TODO can be done even if terminal condition?
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.DEAD) {
                    s.setCondition(PatientCondition.RESURRECTED);
                } else if (s.getCondition().isTerminal()) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .buildInjectionOperation(Fluids.WATER, 500);

    //private static final Operation SEDATE = new Operation.Builder("sedate") // TODO transform in SEDATE_PAIN?
            //.setPainLevel(PainLevel.NEGLIGIBLE)
            //.allowAllLocations()
            //.setMaximumTimesAllowed(-1)
            //.setStatusChangeOnSuccess(s -> {
                //s.setCurrentPain(0);
            //})
            //.buildInjectionOperation(INJECTION_OPERATIONS, Registration.SOURCE_FLUID_SEDATIVE.get(), 72);

    private static final Operation SEDATE = new Operation.Builder("sedate_too_much")
            //.setPainLevel(PainLevel.NEGLIGIBLE)
            .isAdded(s -> s.getCurrentPain() == 0)
            .onConsume(patientStatus -> patientStatus.decreasePain(1))
            .allowAllLocations()
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.ASLEEP_FOREVER))
            .buildInjectionOperation(Registration.SOURCE_FLUID_SEDATIVE.get(), 217);

    private static final Operation SOFTEN = new Operation.Builder("soften")
            .allowAllLocations()
            .buildInjectionOperation(Registration.SOURCE_FLUID_SOFTENER.get(), 57);

    private static final Operation SOFTEN_TOO_MUCH = new Operation.Builder("soften_too_much")
            .allowAllLocations()
            .buildInjectionOperation(Registration.SOURCE_FLUID_SOFTENER.get(), 103);

    private static final Operation SOFTEN_WAY_TOO_MUCH = new Operation.Builder("soften_way_too_much")
            .allowAllLocations()
            .buildInjectionOperation(Registration.SOURCE_FLUID_SOFTENER.get(), 125);

    private static final Operation COAGULATE = new Operation.Builder("coagulate")
            .allowAllLocations()
            .setMaximumTimesAllowed(-1)
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.BLEEDING) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .setEraseFluid(true)
            .buildInjectionOperation(Registration.SOURCE_FLUID_COAGULANT.get(), 32);


}
