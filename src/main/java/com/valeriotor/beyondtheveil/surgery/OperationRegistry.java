package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffectRegistry;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffectType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class OperationRegistry {

    public record InjectionEntry(Operation operation, Fluid fluid, int amount) {
    }

    public record ExtractionEntry(Operation operation, Function<PatientStatus, ItemStack> stack,
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

    public static final String SPINELESS = "spineless";
    public static final String IRON_SPINE = "iron_spine";

    private static final Operation INCISE_BACK = new Operation.Builder("incise_back")
            .setPainPerTick(0.54)
            .setDuration(200)
            .setPainForFailure(40)
            .setSuccessParticles(true)
            .setProgressParticles(true)
            .addPlayerData(PlayerDataLib.incised.name())
            .buildIncisionOperation(SurgicalLocation.BACK);

    private static final Operation INCISE_CHEST = new Operation.Builder("incise_chest")
            .setPainPerTick(1.5)
            .setDuration(160)
            .setPainForFailure(150)
            .setSuccessParticles(true)
            .setProgressParticles(true)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .addPlayerData(PlayerDataLib.incised.name())
            .buildIncisionOperation(SurgicalLocation.CHEST);

    private static final Operation EXTRACT_HEART = new Operation.Builder("extract_heart")
            .setPainPerTick(s -> s.hasString("soften") ? 0.4 : 4)
            .setDuration(180)
            .setPainForFailure(50)
            .setRequirementForSuccessfulCompletion(s -> !s.hasString("soften_too_much"))
            .setCompletionMessage(s -> s.hasString("soften_too_much") ? "surgery.extract_heart.too_soft" : null)
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .setStatusChangeOnSuccess(s -> {
                if(s.getPatientType() != PatientType.WEEPER) {
                    s.setCondition(PatientCondition.DEAD);
                }
            })
            .setPersistent(true)
            .wantsSoften(true)
            .setRequiresIncision(true)
            .setProgressParticles(true)
            .addPlayerData(PlayerDataLib.extracted_heart.name())
            .buildExtractionOperation(EXTRACTION_OPERATIONS, status -> {
                if (status.getPatientType() == PatientType.WEEPER) {
                    return new ItemStack(Items.COAL);
                }
                if (status.getFlags().containsKey("great_heart")) {
                    return new ItemStack(Registration.GREAT_HEART.get());
                } else {
                    return new ItemStack(Registration.HEART.get());
                }
            }, s -> !s.hasString("extract_heart"), false);

    private static final Operation EXTRACT_SPINE = new Operation.Builder("extract_spine")
            .setPainPerTick(0.4)
            .setDuration(180)
            .setPainForFailure(50)
            .addAllowedLocation(SurgicalLocation.BACK)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING))
            .setSuccessSound(BTVSounds.SPINE_RIP.get())
            .setPersistent(true)
            .setRequiresIncision(true)
            .setProgressParticles(true)
            .needsSpine()
            .makeSpineless()
            .addPlayerData(PlayerDataLib.extracted_spine.name())
            .buildExtractionOperation(EXTRACTION_OPERATIONS, s -> s.getFlags().containsKey(IRON_SPINE) ? new ItemStack(Items.IRON_INGOT, new Random().nextInt(10, 13)) : new ItemStack(Registration.SPINE.get()), s-> true, false);

    private static final Operation EXTRACT_BONE_TIARA = new Operation.Builder("extract_bone_tiara")
            .setPainPerTick(0.4)
            .setDuration(180)
            .setPainForFailure(50)
            .addAllowedLocation(SurgicalLocation.BACK)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING))
            .setPersistent(true)
            .setRequiresIncision(true)
            .setProgressParticles(true)
            .needsSpine()
            .makeSpineless()
            .addPlayerData(PlayerDataLib.extracted_bone_tiara.name())
            .buildExtractionOperation(EXTRACTION_OPERATIONS, s -> new ItemStack(Registration.BONE_TIARA.get()), s -> s.getString("insert_emerald_gem") == 3, true);

    private static final Operation FILL_BRAIN = new Operation.Builder("fill_brain")
            .addAllowedLocation(SurgicalLocation.SKULL)
            .setPainPerTick(s -> s.getWaterAmount() < 420 ? 0.47 : 0)
            .setPainForFailure(50)
            .setStatusChangeOnSuccess(PatientStatus::explode)
            // TODO .setEntityChange(s -> new WeeperEntity())
            // TODO sendClientMessage(explode head animation)
            // TODO can be done even if terminal condition?
            //.setStatusChangeOnSuccess(s -> {
            //    if (s.getCondition() == PatientCondition.DEAD) {
            //        s.setCondition(PatientCondition.RESURRECTED);
            //    } else if (s.getCondition().isTerminal()) {
            //        s.setCondition(PatientCondition.STABLE);
            //    }
            //})
            .addPlayerData(PlayerDataLib.created_weeper.name())
            .buildInjectionOperation(Fluids.WATER, 490);

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
            .isAdded(s -> s.getCurrentPain() <= 0.1)
            .onConsume(patientStatus -> patientStatus.decreasePain(1))
            .allowAllLocations()
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.ASLEEP_FOREVER))
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SEDATIVE.get(), 217);

    private static final Operation SOFTEN = new Operation.Builder("soften")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 57);

    private static final Operation SOFTEN_TOO_MUCH = new Operation.Builder("soften_too_much")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 103);

    private static final Operation SOFTEN_WAY_TOO_MUCH = new Operation.Builder("soften_way_too_much")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 125);

    private static final Operation COAGULATE = new Operation.Builder("coagulate")
            .allowAllLocations()
            .setMaximumTimesAllowed(-1)
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.BLEEDING) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .setEraseFluid(true)
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_COAGULANT.get(), 32);

    private static final Operation MEMORY_HORMONES = new Operation.Builder("memory_hormones")
            .addAllowedLocation(SurgicalLocation.SKULL)
            .setPainPerTick(1.2)
            .setPersistent(true)
            .setPainForFailure(75)
            .setSuccessParticles(true)
            .setParticleOffset(new Vec3(0, 0, 1))
            .setSuccessParticleType(ParticleTypes.CRIT)
            .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
            .setSuccessParticleCount(5)
            .buildInjectionOperation(BTVFluids.FLUID_MEMORY_HORMONES.getA().get(), 45);

    private static final Operation OBEDIENCE_HORMONES = new Operation.Builder("obedience_hormones")
            .addAllowedLocation(SurgicalLocation.SKULL)
            .setPainPerTick(2.1)
            .setPersistent(true)
            .setPainForFailure(75)
            .setSuccessParticles(true)
            .setParticleOffset(new Vec3(0, 0, 1))
            .setSuccessParticleType(ParticleTypes.CRIT)
            .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
            .setSuccessParticleCount(5)
            .addPlayerData(PlayerDataLib.first_skull_operation.name())
            .buildInjectionOperation(BTVFluids.FLUID_OBEDIENCE_HORMONES.getA().get(), 35);

    private static final Operation PARENTAL_HORMONES = new Operation.Builder("parental_hormones")
            .addAllowedLocation(SurgicalLocation.SKULL)
            .setPainPerTick(0.9)
            .setPersistent(true)
            .setPainForFailure(50)
            .setSuccessParticles(true)
            .setParticleOffset(new Vec3(0, 0, 1))
            .setSuccessParticleType(ParticleTypes.CRIT)
            .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
            .setSuccessParticleCount(5)
            .addPlayerData(PlayerDataLib.first_skull_operation.name())
            .buildInjectionOperation(BTVFluids.FLUID_PARENTAL_HORMONES.getA().get(), 60);

    private static final Operation GREAT_HEART = new Operation.Builder("great_heart")
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setPainPerTick(2.5)
            .setPersistent(true)
            .setSuccessParticles(true)
            .setSuccessParticleType(BTVParticles.BLOODSPILL.get())
            .setSuccessParticleCount(5)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .buildInjectionOperation(BTVFluids.FLUID_GROWTH_STIMULANT.getA().get(), 70);

    private static final Operation GREAT_SPINE = new Operation.Builder("great_spine")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(2.5)
            .setPersistent(true)
            .setSuccessParticles(true)
            .setSuccessParticleType(BTVParticles.BLOODSPILL.get())
            .setSuccessParticleCount(5)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.DEAD))
            .buildInjectionOperation(BTVFluids.FLUID_GROWTH_STIMULANT.getA().get(), 25);


    private static final Operation INJECT_MOVEMENT_SPEED_SERUM_FLUID = makeArsenalInjection("inject_movement_speed_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.MOVEMENT_SPEED).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SPEED_SERUM.getA().get(), 60);
    private static final Operation INJECT_MOVEMENT_SLOWDOWN_SERUM_FLUID = makeArsenalInjection("inject_movement_slowdown_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.MOVEMENT_SLOWDOWN).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM.getA().get(), 80);
    private static final Operation INJECT_DIG_SPEED_SERUM_FLUID = makeArsenalInjection("inject_dig_speed_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.DIG_SPEED).buildInjectionOperation(BTVFluids.FLUID_DIG_SPEED_SERUM.getA().get(), 80);
    private static final Operation INJECT_DIG_SLOWDOWN_SERUM_FLUID = makeArsenalInjection("inject_dig_slowdown_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.DIG_SLOWDOWN).buildInjectionOperation(BTVFluids.FLUID_DIG_SLOWDOWN_SERUM.getA().get(), 80);
    private static final Operation INJECT_DAMAGE_BOOST_SERUM_FLUID = makeArsenalInjection("inject_damage_boost_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.DAMAGE_BOOST).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_BOOST_SERUM.getA().get(), 80);
    private static final Operation INJECT_HEAL_SERUM_FLUID = makeArsenalInjection("inject_heal_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.HEAL).buildInjectionOperation(BTVFluids.FLUID_HEAL_SERUM.getA().get(), 80);
    private static final Operation INJECT_HARM_SERUM_FLUID = makeArsenalInjection("inject_harm_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.HARM).buildInjectionOperation(BTVFluids.FLUID_HARM_SERUM.getA().get(), 80);
    private static final Operation INJECT_JUMP_SERUM_FLUID = makeArsenalInjection("inject_jump_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.JUMP).buildInjectionOperation(BTVFluids.FLUID_JUMP_SERUM.getA().get(), 80);
    private static final Operation INJECT_CONFUSION_SERUM_FLUID = makeArsenalInjection("inject_confusion_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.CONFUSION).buildInjectionOperation(BTVFluids.FLUID_CONFUSION_SERUM.getA().get(), 80);
    private static final Operation INJECT_REGENERATION_SERUM_FLUID = makeArsenalInjection("inject_regeneration_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.REGENERATION).buildInjectionOperation(BTVFluids.FLUID_REGENERATION_SERUM.getA().get(), 80);
    private static final Operation INJECT_DAMAGE_RESISTANCE_SERUM_FLUID = makeArsenalInjection("inject_damage_resistance_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.DAMAGE_RESISTANCE).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM.getA().get(), 80);
    private static final Operation INJECT_FIRE_RESISTANCE_SERUM_FLUID = makeArsenalInjection("inject_fire_resistance_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.FIRE_RESISTANCE).buildInjectionOperation(BTVFluids.FLUID_FIRE_RESISTANCE_SERUM.getA().get(), 80);
    private static final Operation INJECT_WATER_BREATHING_SERUM_FLUID = makeArsenalInjection("inject_water_breathing_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.WATER_BREATHING).buildInjectionOperation(BTVFluids.FLUID_WATER_BREATHING_SERUM.getA().get(), 80);
    private static final Operation INJECT_INVISIBILITY_SERUM_FLUID = makeArsenalInjection("inject_invisibility_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.INVISIBILITY).buildInjectionOperation(BTVFluids.FLUID_INVISIBILITY_SERUM.getA().get(), 80);
    private static final Operation INJECT_BLINDNESS_SERUM_FLUID = makeArsenalInjection("inject_blindness_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.BLINDNESS).buildInjectionOperation(BTVFluids.FLUID_BLINDNESS_SERUM.getA().get(), 80);
    private static final Operation INJECT_NIGHT_VISION_SERUM_FLUID = makeArsenalInjection("inject_night_vision_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.NIGHT_VISION).buildInjectionOperation(BTVFluids.FLUID_NIGHT_VISION_SERUM.getA().get(), 80);
    private static final Operation INJECT_HUNGER_SERUM_FLUID = makeArsenalInjection("inject_hunger_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.HUNGER).buildInjectionOperation(BTVFluids.FLUID_HUNGER_SERUM.getA().get(), 80);
    private static final Operation INJECT_WEAKNESS_SERUM_FLUID = makeArsenalInjection("inject_weakness_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.WEAKNESS).buildInjectionOperation(BTVFluids.FLUID_WEAKNESS_SERUM.getA().get(), 80);
    private static final Operation INJECT_POISON_SERUM_FLUID = makeArsenalInjection("inject_poison_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.POISON).buildInjectionOperation(BTVFluids.FLUID_POISON_SERUM.getA().get(), 80);
    private static final Operation INJECT_WITHER_SERUM_FLUID = makeArsenalInjection("inject_wither_serum_fluid", 0.9, 90, 4, ArsenalEffectRegistry.WITHER).buildInjectionOperation(BTVFluids.FLUID_WITHER_SERUM.getA().get(), 80);
    private static final Operation INJECT_LIQUID_GOLD_FLUID = makeArsenalInjection("inject_liquid_gold_fluid", 0.9, 90, 4, ArsenalEffectRegistry.SINK).buildInjectionOperation(BTVFluids.FLUID_LIQUID_GOLD.getA().get(), 80);
    private static final Operation INJECT_ORGANOCHLORIDE_FLUID = makeArsenalInjection("inject_organochloride_fluid", 0.9, 90, 4, ArsenalEffectRegistry.HARM_ARTHROPODS).buildInjectionOperation(BTVFluids.FLUID_ORGANOCHLORIDE.getA().get(), 80);
    private static final Operation INJECT_PHEROMONES_FLUID = makeArsenalInjection("inject_pheromones_fluid", 0.9, 90, 4, ArsenalEffectRegistry.EVERYONE_TARGET).buildInjectionOperation(BTVFluids.FLUID_PHEROMONES.getA().get(), 80);

    private static final Operation INJECT_VASOCONSTRICTOR_FLUID = new Operation.Builder("inject_vasoconstrictor_fluid")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(1.2)
            .setPersistent(true)
            .setPainForFailure(60)
            .setSuccessParticles(true)
            .setParticleOffset(new Vec3(0, 0, 1))
            .setSuccessParticleType(ParticleTypes.CRIT)
            .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
            .setSuccessParticleCount(5)
            .setCapacityRequirement(-10)
            .buildInjectionOperation(BTVFluids.FLUID_VASOCONSTRICTOR.getA().get(), 60);

    private static final Operation INSERT_EMPTY_BLADDER = new Operation.Builder("insert_empty_bladder")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(1.2)
            .setDuration(80)
            .setPainForFailure(50)
            .setCapacityRequirement(-10)
            .setPersistent(true)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.EMPTY_BLADDER.get());


    private static final Operation INSERT_EYE = new Operation.Builder("insert_eye")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(0.2)
            .setDuration(80)
            .setPainForFailure(150)
            .setCapacityRequirement(15)
            .setArsenalEffect(ArsenalEffectRegistry.FOLLY)
            .setPersistent(true)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.PLUCKED_EYE.get());

    private static final Operation INSERT_SHELL = makeArsenalInsertion("insert_shell", 90, 2.5, 55, 16, ArsenalEffectRegistry.VULNERABILITY).buildInsertionOperation(Registration.SHELL.get());
    private static final Operation INSERT_TINY_SKULL = makeArsenalInsertion("insert_tiny_skull", 110, 0.9, 65, 7, ArsenalEffectRegistry.TERROR).buildInsertionOperation(Registration.TINY_SKULL.get());
    private static final Operation INSERT_ACID_GLAND = makeArsenalInsertion("insert_acid_gland", 60, 0.9, 80, 12, ArsenalEffectRegistry.DAMAGE_ARMOR).buildInsertionOperation(Registration.ACID_GLAND.get());
    private static final Operation INSERT_SILK_GLAND = makeArsenalInsertion("insert_silk_gland", 60, 0.9, 120, 4, ArsenalEffectRegistry.ENWEB).buildInsertionOperation(Registration.SILK_GLAND.get());

    private static final Operation INSERT_EMERALD_GEM = new Operation.Builder("insert_emerald_gem")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(s -> s.hasString("soften") ? 0.4 : 4)
            .wantsSoften(true)
            .setDuration(80)
            .setPainForFailure(150)
            .setStatusChangeOnSuccess(s -> {
                s.setFluidAmount(BTVFluids.SOURCE_FLUID_SOFTENER.get(), s.getFluidAmount(BTVFluids.SOURCE_FLUID_SOFTENER.get()) - 57);
                s.removeString("soften");
            })
            .setPersistent(true)
            .setMaximumTimesAllowed(3)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.EMERALD_GEM.get());

    private static final Operation INSERT_LIVING_IRON = new Operation.Builder("insert_living_iron")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(0.35)
            .setDuration(90)
            .setPainForFailure(120)
            .setPersistent(true)
            .setMaximumTimesAllowed(1)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.LIVING_IRON.get());

    private static Operation.Builder makeArsenalInsertion(String name, int duration, double pain, double painForFailure, int capacity, ArsenalEffectType effect) {
        return new Operation.Builder(name)
                .addAllowedLocation(SurgicalLocation.BACK)
                .setDuration(duration)
                .setPainPerTick(pain)
                .setPainForFailure(painForFailure)
                .setCapacityRequirement(capacity)
                .setArsenalEffect(effect)
                .setPersistent(true)
                .setProgressParticles(true);
    }

    private static Operation.Builder makeArsenalInjection(String name, double pain, double painForFailure, int capacity, ArsenalEffectType effect) {
        return new Operation.Builder(name)
                .addAllowedLocation(SurgicalLocation.BACK)
                .setPainPerTick(pain)
                .setPersistent(true)
                .setPainForFailure(painForFailure)
                .setSuccessParticles(true)
                .setParticleOffset(new Vec3(0, 0, 1))
                .setSuccessParticleType(ParticleTypes.CRIT)
                .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
                .setSuccessParticleCount(5)
                .setCapacityRequirement(capacity)
                .setArsenalEffect(effect);
    }
}
