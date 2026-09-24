package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffectRegistry;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffectType;
import com.valeriotor.beyondtheveil.surgery.arsenal.TargetingType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
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
    private static final Map<String, MobEffect> CHEST_EFFECT_INJECTIONS = new HashMap<>();
    private static final Map<MobEffect, String> CHEST_EFFECT_INJECTION_REVERSE = new HashMap<>();

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
    static final Map<String, Item> INSERTION_OPERATION_TO_ITEM = new HashMap<>();

    public static Item itemForInsertionOperation(String operationName) {
        return INSERTION_OPERATION_TO_ITEM.get(operationName);
    }

    public static final String SPINELESS = "spineless";
    public static final String IRON_SPINE = "iron_spine";

    public static final Operation INCISE_BACK = new Operation.Builder("incise_back")
            .setPainPerTick(0.54)
            .setDuration(200)
            .setPainForFailure(40)
            .setSuccessParticles(true)
            .setProgressParticles(true)
            .addPlayerData(PlayerDataLib.incised.name())
            .buildIncisionOperation(SurgicalLocation.BACK);

    public static final Operation INCISE_CHEST = new Operation.Builder("incise_chest")
            .setPainPerTick(1.5)
            .setDuration(160)
            .setPainForFailure(150)
            .setSuccessParticles(true)
            .setProgressParticles(true)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .addPlayerData(PlayerDataLib.incised.name())
            .buildIncisionOperation(SurgicalLocation.CHEST);

    public static final Operation EXTRACT_HEART = new Operation.Builder("extract_heart")
            .setPainPerTick(s -> s.hasString("soften") ? 0.4 : 4)
            .setDuration(180)
            .setPainForFailure(50)
            .setRequirementForSuccessfulCompletion(s -> !s.hasString("soften_too_much"))
            .setCompletionMessage(s -> s.hasString("soften_too_much") ? "surgery.extract_heart.too_soft" : null)
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .setStatusChangeOnSuccess(s -> {
                if (s.getPatientType() != PatientType.WEEPER) {
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
                if (status.getFlags().getOrDefault("triggering_serum_chest", 0) > 0 && status.getFlags().getOrDefault("targeting_serum_chest", 0) > 0
                        && status.getFlags().getOrDefault("tears_chest_back", 0) > 0 && status.getFlags().getOrDefault("insert_plucked_eye_chest", 0) > 0) {
                    return new ItemStack(Registration.REVELATION_RING.get());
                }
                if (status.getFlags().containsKey("insert_marrow_gland_chest") && status.getFlags().containsKey("insert_silk_gland_chest") && status.getFlags().containsKey("insert_gunpowder_bladder_chest")) {
                    return new ItemStack(Registration.BLOOD_FIST.get());
                }
                if (status.getFlags().getOrDefault("great_heart", 0) > 0) {
                    if (status.getFlags().getOrDefault("inject_diamond_powder_chest", 0) > 0) {
                        return new ItemStack(Items.DIAMOND, 6);
                    }
                    return new ItemStack(Registration.GREAT_HEART.get());
                }

                if (status.getFlags().getOrDefault("inject_diamond_powder_chest", 0) > 0) {
                    return new ItemStack(Items.DIAMOND, 3);
                }
                return new ItemStack(Registration.HEART.get());
            }, s -> !s.hasString("extract_heart"), false);

    public static final Operation EXTRACT_SPINE = new Operation.Builder("extract_spine")
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
            .buildExtractionOperation(EXTRACTION_OPERATIONS, s -> {
                if (s.getFlags().containsKey(IRON_SPINE))
                    return new ItemStack(Items.IRON_INGOT, new Random().nextInt(10, 13));
                if (s.getFlags().getOrDefault("great_spine", 0) > 0)
                    return new ItemStack(Items.BONE_MEAL, new Random().nextInt(48, 64));
                return new ItemStack(Registration.SPINE.get());
            }, s -> true, false);

    public static final Operation EXTRACT_BONE_TIARA = new Operation.Builder("extract_bone_tiara")
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
            .buildExtractionOperation(EXTRACTION_OPERATIONS, s -> new ItemStack(Registration.BONE_TIARA.get()), s -> s.getString("insert_emerald_gem_back") == 3, true);

    public static final Operation FILL_BRAIN = new Operation.Builder("fill_brain")
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
    /**************************************** ALL LOCATION INJECTIONS ****************************************/

    public static final Operation SEDATE = new Operation.Builder("sedate_too_much")
            //.setPainLevel(PainLevel.NEGLIGIBLE)
            .isAdded(s -> s.getCurrentPain() <= 0.1)
            .onConsume(patientStatus -> patientStatus.decreasePain(1))
            .allowAllLocations()
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.ASLEEP_FOREVER))
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SEDATIVE.get(), 217);

    public static final Operation SOFTEN = new Operation.Builder("soften")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 57);

    public static final Operation SOFTEN_TOO_MUCH = new Operation.Builder("soften_too_much")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 103);

    public static final Operation SOFTEN_WAY_TOO_MUCH = new Operation.Builder("soften_way_too_much")
            .allowAllLocations()
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_SOFTENER.get(), 125);

    public static final Operation COAGULATE = new Operation.Builder("coagulate")
            .allowAllLocations()
            .setMaximumTimesAllowed(-1)
            .setStatusChangeOnSuccess(s -> {
                if (s.getCondition() == PatientCondition.BLEEDING) {
                    s.setCondition(PatientCondition.STABLE);
                }
            })
            .setEraseFluid(true)
            .buildInjectionOperation(BTVFluids.SOURCE_FLUID_COAGULANT.get(), 32);

    /**************************************** SKULL INJECTIONS ****************************************/

    public static final Operation MEMORY_HORMONES = new Operation.Builder("memory_hormones")
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

    public static final Operation OBEDIENCE_HORMONES = new Operation.Builder("obedience_hormones")
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

    public static final Operation PARENTAL_HORMONES_SKULL = new Operation.Builder("parental_hormones")
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

    public static final Operation LIQUID_GOLD_SKULL = makeBasicInjection("liquid_gold_skull", 1.5, 110, 0, true, SurgicalLocation.SKULL)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.DEAD))
            .buildInjectionOperation(BTVFluids.FLUID_LIQUID_GOLD.getA().get(), 60);

    public static final Operation DIAMOND_POWDER_SKULL = makeBasicInjection("diamond_powder_skull", 4.5, 30, 0, true, SurgicalLocation.SKULL)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.DEAD))
            .buildInjectionOperation(BTVFluids.FLUID_DIAMOND_POWDER.getA().get(), 10);
    public static final Operation ORGANOCHLORIDE_SKULL = makeBasicInjection("organochloride_skull", 2.5, 110, 0, true, SurgicalLocation.SKULL).buildInjectionOperation(BTVFluids.FLUID_ORGANOCHLORIDE.getA().get(), 60);
    public static final Operation PHEROMONES_SKULL = makeBasicInjection("pheromones_skull", 0.9, 130, 0, true, SurgicalLocation.SKULL).buildInjectionOperation(BTVFluids.FLUID_PHEROMONES.getA().get(), 54); // Code in ConvalescentData::tick
    public static final Operation TEARS_SKULL = makeBasicInjection("tears_skull", 0.9, 130, 0, true, SurgicalLocation.SKULL)
            .setStatusChangeOnSuccess(PatientStatus::explode)
            .addPlayerData(PlayerDataLib.created_weeper.name())
            .buildInjectionOperation(BTVFluids.FLUID_TEARS.getA().get(), 30);


    /**************************************** BACK & SKULL INJECTIONS ****************************************/

    public static final Operation TRIGGERING_SERUM_BACK_SKULL_HOSTILE_NEARBY = makeBasicInjection("triggering_serum_back_skull_hostile_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.HOSTILE_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 1);
    public static final Operation TRIGGERING_SERUM_BACK_SKULL_IMMORTAL_NEARBY = makeBasicInjection("triggering_serum_back_skull_immortal_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.PLAYER_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 6);
    public static final Operation TRIGGERING_SERUM_BACK_SKULL_MASTER_NEARBY = makeBasicInjection("triggering_serum_back_skull_master_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.MASTER_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 11);
    public static final Operation TRIGGERING_SERUM_BACK_SKULL_ATTACK_ABOM = makeBasicInjection("triggering_serum_back_skull_attack_abom", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.WAS_HIT)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 16);
    public static final Operation TRIGGERING_SERUM_BACK_SKULL_ATTACK_MASTER = makeBasicInjection("triggering_serum_back_skull_attack_master", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.MASTER_WAS_HIT)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 21);
    public static final Operation TRIGGERING_SERUM_BACK_SKULL_ATTACKED_BY_MASTER = makeBasicInjection("triggering_serum_back_skull_attacked_by_master", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTriggerType(TargetingType.MASTER_ATTACKED)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 26);

    public static final Operation TARGETING_SERUM_BACK_SKULL_HOSTILE_NEARBY = makeBasicInjection("targeting_serum_back_skull_hostile_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.HOSTILE_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 1);
    public static final Operation TARGETING_SERUM_BACK_SKULL_IMMORTAL_NEARBY = makeBasicInjection("targeting_serum_back_skull_immortal_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.PLAYER_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 6);
    public static final Operation TARGETING_SERUM_BACK_SKULL_MASTER_NEARBY = makeBasicInjection("targeting_serum_back_skull_master_nearby", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.MASTER_NEARBY)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 11);
    public static final Operation TARGETING_SERUM_BACK_SKULL_ATTACK_ABOM = makeBasicInjection("targeting_serum_back_skull_attack_abom", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.WAS_HIT)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 16);
    public static final Operation TARGETING_SERUM_BACK_SKULL_ATTACK_MASTER = makeBasicInjection("targeting_serum_back_skull_attack_master", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.MASTER_WAS_HIT)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 21);
    public static final Operation TARGETING_SERUM_BACK_SKULL_ATTACKED_BY_MASTER = makeBasicInjection("targeting_serum_back_skull_attacked_by_master", 1.1, 110, 0, true, SurgicalLocation.BACK, SurgicalLocation.SKULL)
            .setTargetType(TargetingType.MASTER_ATTACKED)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 26);

    /**************************************** CHEST & BACK INJECTIONS ****************************************/

    public static final Operation MEMORY_HORMONES_CHEST_BACK = makeBasicInjection("memory_hormones_chest_back", 0.5, 140, 0, true, SurgicalLocation.BACK, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_MEMORY_HORMONES.getA().get(), 102);
    public static final Operation PARENTAL_HORMONES_CHEST_BACK = makeBasicInjection("parental_hormones_chest_back", 0.5, 140, 0, true, SurgicalLocation.BACK, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_PARENTAL_HORMONES.getA().get(), 52);

    public static final Operation TEARS_CHEST_BACK = makeBasicInjection("tears_chest_back", 1.1, 110, -10, true, SurgicalLocation.BACK, SurgicalLocation.CHEST)
            .buildInjectionOperation(BTVFluids.FLUID_TEARS.getA().get(), 45);

    /**************************************** CHEST INJECTIONS ****************************************/
    public static final Operation GREAT_HEART = new Operation.Builder("great_heart")
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setPainForFailure(125)
            .setPainPerTick(2.5)
            .setPersistent(true)
            .setSuccessParticles(true)
            .setSuccessParticleType(BTVParticles.BLOODSPILL.get())
            .setSuccessParticleCount(5)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .buildInjectionOperation(BTVFluids.FLUID_GROWTH_STIMULANT.getA().get(), 70);

    public static final Operation LIQUID_GOLD_CHEST = makeBasicInjection("liquid_gold_chest", 1.5, 110, 0, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_LIQUID_GOLD.getA().get(), 60);
    public static final Operation VASOCONSTRICTOR_CHEST = makeBasicInjection("vasoconstrictor_chest", 2.5, 110, 0, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_VASOCONSTRICTOR.getA().get(), 60);
    public static final Operation ORGANOCHLORIDE_CHEST = makeBasicInjection("organochloride_chest", 0.9, 130, 0, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_ORGANOCHLORIDE.getA().get(), 60); // Code in LivingEvents::targetEvent
    public static final Operation PHEROMONES_CHEST = makeBasicInjection("pheromones_chest", 0.9, 130, 0, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_PHEROMONES.getA().get(), 60); // Code in ConvalescentData::tick

    public static final Operation DIAMOND_POWDER_CHEST = makeBasicInjection("diamond_powder_chest", 2.5, 90, 5, true, SurgicalLocation.CHEST)
            .setIncreaseArsenalEffectAmplifier(true)
            .wantsSoften(true)
            .setPainPerTick(s -> s.hasString("soften") ? 2.5 : 6)
            .buildInjectionOperation(BTVFluids.FLUID_DIAMOND_POWDER.getA().get(), 80);

    public static final Operation TRIGGERING_SERUM_CHEST = makeBasicInjection("triggering_serum_chest", 1.1, 110, 0, true, SurgicalLocation.CHEST)
            .buildInjectionOperation(BTVFluids.FLUID_GS121_SERUM.getA().get(), 15);

    public static final Operation TARGETING_SERUM_CHEST = makeBasicInjection("targeting_serum_chest", 1.1, 110, 0, true, SurgicalLocation.CHEST)
            .buildInjectionOperation(BTVFluids.FLUID_SA245_SERUM.getA().get(), 15);

    public static final Operation MOVEMENT_SPEED_SERUM_CHEST = chestEffectInjection(makeBasicInjection("movement_speed_serum_chest", 1.5, 120, 4, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SPEED_SERUM.getA().get(), 80), MobEffects.MOVEMENT_SPEED);
    public static final Operation MOVEMENT_SLOWDOWN_SERUM_CHEST = chestEffectInjection(makeBasicInjection("movement_slowdown_serum_chest", 1.5, 120, 4, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM.getA().get(), 80), MobEffects.MOVEMENT_SLOWDOWN);
    public static final Operation DIG_SPEED_SERUM_CHEST = chestEffectInjection(makeBasicInjection("dig_speed_serum_chest", 1.5, 120, 3, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_DIG_SPEED_SERUM.getA().get(), 80), MobEffects.DIG_SPEED);
    public static final Operation DIG_SLOWDOWN_SERUM_CHEST = chestEffectInjection(makeBasicInjection("dig_slowdown_serum_chest", 1.5, 120, 5, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_DIG_SLOWDOWN_SERUM.getA().get(), 80), MobEffects.DIG_SLOWDOWN);
    public static final Operation DAMAGE_BOOST_SERUM_CHEST = chestEffectInjection(makeBasicInjection("damage_boost_serum_chest", 1.5, 120, 8, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_BOOST_SERUM.getA().get(), 80), MobEffects.DAMAGE_BOOST);
    public static final Operation HEAL_SERUM_CHEST = chestEffectInjection(makeBasicInjection("heal_serum_chest", 1.5, 120, 9, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_HEAL_SERUM.getA().get(), 80), MobEffects.HEAL);
    public static final Operation HARM_SERUM_CHEST = chestEffectInjection(makeBasicInjection("harm_serum_chest", 1.5, 120, 10, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_HARM_SERUM.getA().get(), 80), MobEffects.HARM);
    public static final Operation JUMP_SERUM_CHEST = chestEffectInjection(makeBasicInjection("jump_serum_chest", 1.5, 120, 4, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_JUMP_SERUM.getA().get(), 80), MobEffects.JUMP);
    public static final Operation CONFUSION_SERUM_CHEST = chestEffectInjection(makeBasicInjection("confusion_serum_chest", 1.5, 120, 1, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_CONFUSION_SERUM.getA().get(), 80), MobEffects.CONFUSION);
    public static final Operation REGENERATION_SERUM_CHEST = chestEffectInjection(makeBasicInjection("regeneration_serum_chest", 1.5, 120, 5, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_REGENERATION_SERUM.getA().get(), 80), MobEffects.REGENERATION);
    public static final Operation DAMAGE_RESISTANCE_SERUM_CHEST = chestEffectInjection(makeBasicInjection("damage_resistance_serum_chest", 1.5, 120, 10, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM.getA().get(), 80), MobEffects.DAMAGE_RESISTANCE);
    public static final Operation FIRE_RESISTANCE_SERUM_CHEST = chestEffectInjection(makeBasicInjection("fire_resistance_serum_chest", 1.5, 120, 1, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_FIRE_RESISTANCE_SERUM.getA().get(), 80), MobEffects.FIRE_RESISTANCE);
    public static final Operation WATER_BREATHING_SERUM_CHEST = chestEffectInjection(makeBasicInjection("water_breathing_serum_chest", 1.5, 120, 1, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_WATER_BREATHING_SERUM.getA().get(), 80), MobEffects.WATER_BREATHING);
    public static final Operation INVISIBILITY_SERUM_CHEST = chestEffectInjection(makeBasicInjection("invisibility_serum_chest", 1.5, 120, 4, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_INVISIBILITY_SERUM.getA().get(), 80), MobEffects.INVISIBILITY);
    public static final Operation BLINDNESS_SERUM_CHEST = chestEffectInjection(makeBasicInjection("blindness_serum_chest", 1.5, 120, 4, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_BLINDNESS_SERUM.getA().get(), 80), MobEffects.BLINDNESS);
    public static final Operation NIGHT_VISION_SERUM_CHEST = chestEffectInjection(makeBasicInjection("night_vision_serum_chest", 1.5, 120, 2, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_NIGHT_VISION_SERUM.getA().get(), 80), MobEffects.NIGHT_VISION);
    public static final Operation HUNGER_SERUM_CHEST = chestEffectInjection(makeBasicInjection("hunger_serum_chest", 1.5, 120, 5, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_HUNGER_SERUM.getA().get(), 80), MobEffects.HUNGER);
    public static final Operation WEAKNESS_SERUM_CHEST = chestEffectInjection(makeBasicInjection("weakness_serum_chest", 1.5, 120, 7, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_WEAKNESS_SERUM.getA().get(), 80), MobEffects.WEAKNESS);
    public static final Operation POISON_SERUM_CHEST = chestEffectInjection(makeBasicInjection("poison_serum_chest", 1.5, 120, 5, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_POISON_SERUM.getA().get(), 80), MobEffects.POISON);
    public static final Operation WITHER_SERUM_CHEST = chestEffectInjection(makeBasicInjection("wither_serum_chest", 1.5, 120, 6, true, SurgicalLocation.CHEST).buildInjectionOperation(BTVFluids.FLUID_WITHER_SERUM.getA().get(), 80), MobEffects.WITHER);



    /**************************************** BACK INJECTIONS ****************************************/

    public static final Operation INJECT_VASOCONSTRICTOR_FLUID = new Operation.Builder("inject_vasoconstrictor_fluid")
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

    public static final Operation GREAT_SPINE = new Operation.Builder("great_spine")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainForFailure(125)
            .setPainPerTick(2.5)
            .setPersistent(true)
            .setSuccessParticles(true)
            .setSuccessParticleType(BTVParticles.BLOODSPILL.get())
            .setSuccessParticleCount(5)
            .setParticleOffset(new Vec3(-0.1, 0, 0.15))
            .setSuccessSound(BTVSounds.HEART_RIP.get())
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.DEAD))
            .buildInjectionOperation(BTVFluids.FLUID_GROWTH_STIMULANT.getA().get(), 25);


    public static final Operation INJECT_MOVEMENT_SPEED_SERUM_BACK = makeArsenalInjection("inject_movement_speed_serum_back", 0.9, 90, 4, ArsenalEffectRegistry.MOVEMENT_SPEED).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SPEED_SERUM.getA().get(), 60);
    public static final Operation INJECT_MOVEMENT_SLOWDOWN_SERUM_BACK = makeArsenalInjection("inject_movement_slowdown_serum_back", 0.9, 90, 4, ArsenalEffectRegistry.MOVEMENT_SLOWDOWN).buildInjectionOperation(BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM.getA().get(), 80);
    public static final Operation INJECT_DIG_SPEED_SERUM_BACK = makeArsenalInjection("inject_dig_speed_serum_back", 0.9, 90, 3, ArsenalEffectRegistry.DIG_SPEED).buildInjectionOperation(BTVFluids.FLUID_DIG_SPEED_SERUM.getA().get(), 80);
    public static final Operation INJECT_DIG_SLOWDOWN_SERUM_BACK = makeArsenalInjection("inject_dig_slowdown_serum_back", 0.9, 90, 5, ArsenalEffectRegistry.DIG_SLOWDOWN).buildInjectionOperation(BTVFluids.FLUID_DIG_SLOWDOWN_SERUM.getA().get(), 80);
    public static final Operation INJECT_DAMAGE_BOOST_SERUM_BACK = makeArsenalInjection("inject_damage_boost_serum_back", 0.9, 90, 8, ArsenalEffectRegistry.DAMAGE_BOOST).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_BOOST_SERUM.getA().get(), 80);
    public static final Operation INJECT_HEAL_SERUM_BACK = makeArsenalInjection("inject_heal_serum_back", 0.9, 90, 9, ArsenalEffectRegistry.HEAL).buildInjectionOperation(BTVFluids.FLUID_HEAL_SERUM.getA().get(), 80);
    public static final Operation INJECT_HARM_SERUM_BACK = makeArsenalInjection("inject_harm_serum_back", 0.9, 90, 10, ArsenalEffectRegistry.HARM).buildInjectionOperation(BTVFluids.FLUID_HARM_SERUM.getA().get(), 80);
    public static final Operation INJECT_JUMP_SERUM_BACK = makeArsenalInjection("inject_jump_serum_back", 0.9, 90, 4, ArsenalEffectRegistry.JUMP).buildInjectionOperation(BTVFluids.FLUID_JUMP_SERUM.getA().get(), 80);
    public static final Operation INJECT_CONFUSION_SERUM_BACK = makeArsenalInjection("inject_confusion_serum_back", 0.9, 90, 1, ArsenalEffectRegistry.CONFUSION).buildInjectionOperation(BTVFluids.FLUID_CONFUSION_SERUM.getA().get(), 80);
    public static final Operation INJECT_REGENERATION_SERUM_BACK = makeArsenalInjection("inject_regeneration_serum_back", 0.9, 90, 5, ArsenalEffectRegistry.REGENERATION).buildInjectionOperation(BTVFluids.FLUID_REGENERATION_SERUM.getA().get(), 80);
    public static final Operation INJECT_DAMAGE_RESISTANCE_SERUM_BACK = makeArsenalInjection("inject_damage_resistance_serum_back", 0.9, 90, 10, ArsenalEffectRegistry.DAMAGE_RESISTANCE).buildInjectionOperation(BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM.getA().get(), 80);
    public static final Operation INJECT_FIRE_RESISTANCE_SERUM_BACK = makeArsenalInjection("inject_fire_resistance_serum_back", 0.9, 90, 1, ArsenalEffectRegistry.FIRE_RESISTANCE).buildInjectionOperation(BTVFluids.FLUID_FIRE_RESISTANCE_SERUM.getA().get(), 80);
    public static final Operation INJECT_WATER_BREATHING_SERUM_BACK = makeArsenalInjection("inject_water_breathing_serum_back", 0.9, 90, 1, ArsenalEffectRegistry.WATER_BREATHING).buildInjectionOperation(BTVFluids.FLUID_WATER_BREATHING_SERUM.getA().get(), 80);
    public static final Operation INJECT_INVISIBILITY_SERUM_BACK = makeArsenalInjection("inject_invisibility_serum_back", 0.9, 90, 4, ArsenalEffectRegistry.INVISIBILITY).buildInjectionOperation(BTVFluids.FLUID_INVISIBILITY_SERUM.getA().get(), 80);
    public static final Operation INJECT_BLINDNESS_SERUM_BACK = makeArsenalInjection("inject_blindness_serum_back", 0.9, 90, 4, ArsenalEffectRegistry.BLINDNESS).buildInjectionOperation(BTVFluids.FLUID_BLINDNESS_SERUM.getA().get(), 80);
    public static final Operation INJECT_NIGHT_VISION_SERUM_BACK = makeArsenalInjection("inject_night_vision_serum_back", 0.9, 90, 2, ArsenalEffectRegistry.NIGHT_VISION).buildInjectionOperation(BTVFluids.FLUID_NIGHT_VISION_SERUM.getA().get(), 80);
    public static final Operation INJECT_HUNGER_SERUM_BACK = makeArsenalInjection("inject_hunger_serum_back", 0.9, 90, 5, ArsenalEffectRegistry.HUNGER).buildInjectionOperation(BTVFluids.FLUID_HUNGER_SERUM.getA().get(), 80);
    public static final Operation INJECT_WEAKNESS_SERUM_BACK = makeArsenalInjection("inject_weakness_serum_back", 0.9, 90, 7, ArsenalEffectRegistry.WEAKNESS).buildInjectionOperation(BTVFluids.FLUID_WEAKNESS_SERUM.getA().get(), 80);
    public static final Operation INJECT_POISON_SERUM_BACK = makeArsenalInjection("inject_poison_serum_back", 0.9, 90, 5, ArsenalEffectRegistry.POISON).buildInjectionOperation(BTVFluids.FLUID_POISON_SERUM.getA().get(), 80);
    public static final Operation INJECT_WITHER_SERUM_BACK = makeArsenalInjection("inject_wither_serum_back", 0.9, 90, 6, ArsenalEffectRegistry.WITHER).buildInjectionOperation(BTVFluids.FLUID_WITHER_SERUM.getA().get(), 80);
    public static final Operation INJECT_LIQUID_GOLD_BACK = makeArsenalInjection("inject_liquid_gold_back", 0.9, 90, 5, ArsenalEffectRegistry.SINK).buildInjectionOperation(BTVFluids.FLUID_LIQUID_GOLD.getA().get(), 80);
    public static final Operation INJECT_ORGANOCHLORIDE_BACK = makeArsenalInjection("inject_organochloride_back", 0.9, 90, 6, ArsenalEffectRegistry.HARM_ARTHROPODS).buildInjectionOperation(BTVFluids.FLUID_ORGANOCHLORIDE.getA().get(), 80);
    public static final Operation INJECT_PHEROMONES_BACK = makeArsenalInjection("inject_pheromones_back", 0.9, 90, 11, ArsenalEffectRegistry.EVERYONE_TARGET).buildInjectionOperation(BTVFluids.FLUID_PHEROMONES.getA().get(), 80);

    public static final Operation DIAMOND_POWDER_BACK = makeBasicInjection("diamond_powder_back", 2.5, 90, 5, true, SurgicalLocation.BACK)
            .setIncreaseArsenalEffectAmplifier(true)
            .wantsSoften(true)
            .setPainPerTick(s -> s.hasString("soften") ? 2.5 : 6)
            .buildInjectionOperation(BTVFluids.FLUID_DIAMOND_POWDER.getA().get(), 80);



    /**************************************** CHEST & BACK INSERTIONS ****************************************/


    public static final Operation INSERT_EMPTY_BLADDER = new Operation.Builder("insert_empty_bladder")
            .addAllowedLocation(SurgicalLocation.BACK)
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setPainPerTick(1.2)
            .setDuration(80)
            .setPainForFailure(50)
            .setCapacityRequirement(-10)
            .setPersistent(true)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.EMPTY_BLADDER.get());


    /**************************************** BACK INSERTIONS ****************************************/

    public static final Operation INSERT_EYE_BACK = makeArsenalInsertion("insert_eye_back", 80, 0.4, 150, 7, ArsenalEffectRegistry.FOLLY).buildInsertionOperation(Registration.PLUCKED_EYE.get());
    public static final Operation INSERT_SHELL_BACK = makeArsenalInsertion("insert_shell_back", 90, 2.5, 55, 15, ArsenalEffectRegistry.VULNERABILITY)
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING)).buildInsertionOperation(Registration.SHELL.get());
    public static final Operation INSERT_TINY_SKULL_BACK = makeArsenalInsertion("insert_tiny_skull_back", 110, 0.9, 65, 7, ArsenalEffectRegistry.TERROR).buildInsertionOperation(Registration.TINY_SKULL.get());
    public static final Operation INSERT_ACID_GLAND_BACK = makeArsenalInsertion("insert_acid_gland_back", 60, 0.9, 80, 7, ArsenalEffectRegistry.DAMAGE_ARMOR).buildInsertionOperation(Registration.ACID_GLAND.get());
    public static final Operation INSERT_SILK_GLAND_BACK = makeArsenalInsertion("insert_silk_gland_back", 60, 0.9, 120, 4, ArsenalEffectRegistry.ENWEB).buildInsertionOperation(Registration.SILK_GLAND.get());
    public static final Operation INSERT_SCALES_BACK = makeArsenalInsertion("insert_scales_back", 100, 1.8, 120, 12, ArsenalEffectRegistry.DROP_ITEM).buildInsertionOperation(Registration.SCALES.get());
    public static final Operation INSERT_SWOLLEN_GROWTH_BACK = makeArsenalInsertion("insert_swollen_growth_back", 200, 1.2, 120, 15, ArsenalEffectRegistry.DISROBE)
            .wantsSoften(true)
            .setPainPerTick(s -> s.hasString("soften") ? 1.2 : 5)
            .buildInsertionOperation(Registration.SWOLLEN_GROWTH.get());
    public static final Operation INSERT_PERIOSTEUM_GROWTH_BACK = makeArsenalInsertion("insert_periosteum_growth_back", 70, 1.4, 120, 12, ArsenalEffectRegistry.FEARSOME).buildInsertionOperation(Registration.PERIOSTEUM_GROWTH.get());
    public static final Operation INSERT_CHROMATOPHORE_GLAND_BACK = makeArsenalInsertion("insert_chromatophore_gland_back", 100, 0.1, 140, 3, ArsenalEffectRegistry.CAMOUFLAGE).buildInsertionOperation(Registration.CHROMATOPHORE_GLAND.get());
    public static final Operation INSERT_OSTEOCLAST_GLAND_BACK = makeArsenalInsertion("insert_osteoclast_gland_back", 100, 0.6, 140, 5, ArsenalEffectRegistry.HARM_UNDEAD).buildInsertionOperation(Registration.OSTEOCLAST_GLAND.get());
    public static final Operation INSERT_SLIME_HEART_BACK = makeArsenalInsertion("insert_slime_heart_back", 70, 0.4, 140, 2, ArsenalEffectRegistry.CREATE_SLIME).buildInsertionOperation(Registration.SLIME_HEART.get());

    public static final Operation INSERT_EMERALD_GEM_BACK = new Operation.Builder("insert_emerald_gem_back")
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

    public static final Operation INSERT_LIVING_IRON_BACK = new Operation.Builder("insert_living_iron_back")
            .addAllowedLocation(SurgicalLocation.BACK)
            .setPainPerTick(0.35)
            .setDuration(90)
            .setPainForFailure(120)
            .setPersistent(true)
            .setMaximumTimesAllowed(1)
            .setProgressParticles(true)
            .buildInsertionOperation(Registration.LIVING_IRON.get());

    public static final Operation INSERT_MARROW_GLAND_BACK = makeBasicInsertion("insert_marrow_gland_back", 60, 0.9, 120, SurgicalLocation.BACK).buildInsertionOperation(Registration.MARROW_GLAND.get()); // code in ConvalescentData::tick
    public static final Operation INSERT_FERTILIZER_GLAND_BACK = makeBasicInsertion("insert_fertilizer_gland_back", 60, 0.9, 120, SurgicalLocation.BACK).buildInsertionOperation(Registration.FERTILIZER_GLAND.get()); // code in ConvalescentData::tick
    public static final Operation INSERT_GUNPOWDER_BLADDER_BACK = makeBasicInsertion("insert_gunpowder_bladder_back", 110, 0.9, 120, SurgicalLocation.BACK).buildInsertionOperation(Registration.GUNPOWDER_BLADDER.get()); // code in ConvalescentData::tick

    /**************************************** CHEST INSERTIONS ****************************************/

    public static final Operation INSERT_SCALES_CHEST = new Operation.Builder("insert_scales_chest")
            .addAllowedLocation(SurgicalLocation.CHEST)
            .setPainPerTick(0.35)
            .setDuration(80)
            .setPainForFailure(100)
            .setPersistent(true)
            .setMaximumTimesAllowed(3)
            .setProgressParticles(true)
            .addPlayerData(s -> s.getFlags().getOrDefault("insert_scales_chest", 0) >= 3, PlayerDataLib.scaled_crawler.name())
            .buildInsertionOperation(Registration.SCALES.get());

    public static final Operation INSERT_SHELL_CHEST = makeBasicInsertion("insert_shell_chest", 150, 1.5, 120, SurgicalLocation.CHEST) // code in AttackEvents::livingDamageEvent
            .setStatusChangeOnSuccess(s -> s.setCondition(PatientCondition.BLEEDING)).buildInsertionOperation(Registration.SHELL.get());

    public static final Operation INSERT_TINY_SKULL_CHEST = makeBasicInsertion("insert_tiny_skull_chest", 60, 0.9, 120, SurgicalLocation.CHEST).buildInsertionOperation(Registration.TINY_SKULL.get()); // code in LivingEvents::livingDeathEvent
    public static final Operation INSERT_ACID_GLAND_CHEST = makeBasicInsertion("insert_acid_gland_chest", 105, 1.8, 120, SurgicalLocation.CHEST) // code in LivingEvents::livingDeathEvent
            .setMaximumTimesAllowed(3).buildInsertionOperation(Registration.ACID_GLAND.get());

    public static final Operation INSERT_MARROW_GLAND_CHEST = makeBasicInsertion("insert_marrow_gland_chest", 105, 0.4, 120, SurgicalLocation.CHEST).buildInsertionOperation(Registration.MARROW_GLAND.get()); // part of blood fist
    public static final Operation INSERT_SILK_GLAND_CHEST = makeBasicInsertion("insert_silk_gland_chest", 105, 0.4, 120, SurgicalLocation.CHEST).buildInsertionOperation(Registration.SILK_GLAND.get()); // part of blood fist
    public static final Operation INSERT_GUNPOWDER_BLADDER_CHEST = makeBasicInsertion("insert_gunpowder_bladder_chest", 105, 0.4, 120, SurgicalLocation.CHEST).buildInsertionOperation(Registration.GUNPOWDER_BLADDER.get()); // part of blood fist

    public static final Operation INSERT_PERIOSTEUM_GROWTH_CHEST = makeBasicInsertion("insert_periosteum_growth_chest", 105, 1.1, 110, SurgicalLocation.CHEST).buildInsertionOperation(Registration.PERIOSTEUM_GROWTH.get()); // code in LivingEvents::targetEvent
    public static final Operation INSERT_OSTEOCLAST_GLAND_CHEST = makeBasicInsertion("insert_osteoclast_gland_chest", 105, 1.3, 110, SurgicalLocation.CHEST).buildInsertionOperation(Registration.OSTEOCLAST_GLAND.get()); // code in LivingEvents::targetEvent
    public static final Operation INSERT_SLIME_HEART_CHEST = makeBasicInsertion("insert_slime_heart_chest", 105, 1.6, 110, SurgicalLocation.CHEST).buildInsertionOperation(Registration.SLIME_HEART.get()); // code in LivingEvents::targetEvent and AttackEvents::livingAttackEvent

    public static final Operation INSERT_LIVING_IRON_CHEST = makeBasicInsertion("insert_living_iron_chest", 85, 0.4, 110, SurgicalLocation.CHEST).buildInsertionOperation(Registration.LIVING_IRON.get()); // code in ConvalescentData::applyConvalescentAttributes
    public static final Operation INSERT_PLUCKED_EYE_CHEST = makeBasicInsertion("insert_plucked_eye_chest", 115, 0.4, 110, SurgicalLocation.CHEST).buildInsertionOperation(Registration.PLUCKED_EYE.get()); // code in ConvalescentData::applyConvalescentAttributes
    public static final Operation INSERT_SWOLLEN_GROWTH_CHEST = makeBasicInsertion("insert_swollen_growth_chest", 85, 0.4, 110, SurgicalLocation.CHEST)
            .wantsSoften(true)
            .setPainPerTick(s -> s.hasString("soften") ? 1.2 : 5)
            .buildInsertionOperation(Registration.SWOLLEN_GROWTH.get()); // code in ConvalescentData::tick
    public static final Operation INSERT_EMERALD_GEM_CHEST = makeBasicInsertion("insert_emerald_gem_chest", 85, 0.4, 110, SurgicalLocation.CHEST)
            .wantsSoften(true)
            .setPainPerTick(s -> s.hasString("soften") ? 1.2 : 5)
            .buildInsertionOperation(Registration.EMERALD_GEM.get()); // code in ConvalescentData::tick


    /**************************************** HELPER METHODS ****************************************/


    private static Operation.Builder makeBasicInsertion(String name, int duration, double pain, double painForFailure, SurgicalLocation... locations) {
        Operation.Builder builder = new Operation.Builder(name)
                .setDuration(duration)
                .setPersistent(true)
                .setPainPerTick(pain)
                .setPainForFailure(painForFailure);
        for (SurgicalLocation location : locations) {
            builder.addAllowedLocation(location);
        }
        return builder;
    }

    private static Operation.Builder makeArsenalInsertion(String name, int duration, double pain, double painForFailure, int capacity, ArsenalEffectType effect) {
        return makeBasicInsertion(name, duration, pain, painForFailure, SurgicalLocation.BACK)
                .setCapacityRequirement(capacity)
                .setArsenalEffect(effect)
                .setProgressParticles(true);
    }

    private static Operation.Builder makeBasicInjection(String name, double pain, double painForFailure, int capacity, boolean successIndicator, SurgicalLocation... locations) {
        Operation.Builder builder = new Operation.Builder(name)
                .setPersistent(true)
                .setPainPerTick(pain)
                .setPainForFailure(painForFailure)
                .setCapacityRequirement(capacity);
        if (successIndicator) {
            builder.setSuccessParticles(true)
                    .setParticleOffset(new Vec3(0, 0, 1))
                    .setSuccessParticleType(ParticleTypes.CRIT)
                    .setSuccessSound(SoundEvents.EXPERIENCE_ORB_PICKUP)
                    .setSuccessParticleCount(5);
        }
        for (SurgicalLocation location : locations) {
            builder.addAllowedLocation(location);
        }
        return builder;
    }

    private static Operation.Builder makeArsenalInjection(String name, double pain, double painForFailure, int capacity, ArsenalEffectType effect) {
        return makeBasicInjection(name, pain, painForFailure, capacity, true, SurgicalLocation.BACK)
                .setArsenalEffect(effect);
    }



    private static Operation chestEffectInjection(Operation operation, MobEffect effect) {
        CHEST_EFFECT_INJECTIONS.put(operation.getName(), effect);
        CHEST_EFFECT_INJECTION_REVERSE.put(effect, operation.getName());
        return operation;
    }

    public static MobEffect getChestEffectInjection(String name) {
        return CHEST_EFFECT_INJECTIONS.get(name);
    }

    public static String getNameForChestEffectInjection(MobEffect effect) {
        return CHEST_EFFECT_INJECTION_REVERSE.get(effect);
    }
}
