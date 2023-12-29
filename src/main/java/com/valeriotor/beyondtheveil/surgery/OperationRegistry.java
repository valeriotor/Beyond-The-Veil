package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.Registration;
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

    static final Map<String, Operation> OPERATIONS_BY_NAME = new HashMap<>();
    static final Map<Fluid, List<InjectionEntry>> INJECTION_OPERATIONS = new HashMap<>();
    static final List<ExtractionEntry> EXTRACTION_OPERATIONS = new ArrayList<>();
    static final Map<Item, List<InsertionEntry>> INSERTION_OPERATIONS = new HashMap<>();

    private final Operation EXTRACT_HEART = new Operation.Builder("extract_heart")
            .setPainLevel(s -> s.hasString("softened") ? PainLevel.MEDIUM : PainLevel.EXTREME)
            .setRequirementForSuccessfulCompletion(s -> !s.hasString("too_soft"))
            .setCompletionMessage(s -> s.hasString("too_soft") ? "surgery.extract_heart.too_soft" : null)
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setPersistent(true)
            .buildExtractionOperation(EXTRACTION_OPERATIONS, new ItemStack(Registration.HEART.get()));

    private final Operation EXTRACT_SPINE = new Operation.Builder("extract_spine")
            .setPainLevel(s -> s.hasString("softened") ? PainLevel.MEDIUM : PainLevel.EXTREME)
            .addAllowedLocation(SurgicalLocation.BACK)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING))
            .setPersistent(true)
            .buildExtractionOperation(EXTRACTION_OPERATIONS, new ItemStack(Registration.HEART.get()));

    private final Operation FILL_BRAIN = new Operation.Builder("fill_brain")
            .setPainLevel(PainLevel.HIGH)
            // TODO .setEntityChange(s -> new WeeperEntity())
            // TODO sendClientMessage(explode head animation)
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.DEAD) {
                    s.setCondition(PatientCondition.RESURRECTED);
                } else if (s.getCondition().isTerminal()) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 500);

    private final Operation SEDATE = new Operation.Builder("sedate")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .setMaximumTimesAllowed(-1)
            .setStatusChangeOnSuccess(s -> {
                s.setCurrentPain(0);
            })
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 72); // TODO change fluid

    private final Operation SEDATE_TOO_MUCH = new Operation.Builder("sedate_too_much")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.ASLEEP_FOREVER))
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 217);

    private final Operation SOFTEN = new Operation.Builder("soften")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 57);


    private final Operation SOFTEN_TOO_MUCH = new Operation.Builder("too_soft")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 103);

    private final Operation SOFTEN_WAY_TOO_MUCH = new Operation.Builder("way_too_soft")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.TOO_SOFT))
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 125);

    private final Operation COAGULATE = new Operation.Builder("coagulate")
            .setPainLevel(PainLevel.NEGLIGIBLE)
            .setMaximumTimesAllowed(-1)
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.BLEEDING) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .setEraseFluid(true)
            .buildInjectionOperation(INJECTION_OPERATIONS, Fluids.WATER, 32);


}
