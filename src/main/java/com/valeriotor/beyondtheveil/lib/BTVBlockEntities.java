package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DreamFocusBlock;
import com.valeriotor.beyondtheveil.tile.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTVBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.MODID);
    public static final RegistryObject<BlockEntityType<PatientPodBE>> PATIENT_POD_BE = BLOCK_ENTITIES.register(Registration.PATIENT_POD.getId().getPath(), () -> BlockEntityType.Builder.of(PatientPodBE::new, Registration.PATIENT_POD.get()).build(null));
    public static final RegistryObject<BlockEntityType<LacrymatoryBE>> LACRYMATORY_BE = BLOCK_ENTITIES.register(Registration.LACRYMATORY.getId().getPath(), () -> BlockEntityType.Builder.of(LacrymatoryBE::new, Registration.LACRYMATORY.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloodBasinBE>> BLOOD_BASIN_BE = BLOCK_ENTITIES.register(Registration.BLOOD_BASIN.getId().getPath(), () -> BlockEntityType.Builder.of(BloodBasinBE::new, Registration.BLOOD_BASIN.get()).build(null));
    public static final RegistryObject<BlockEntityType<SacrificeAltarBE>> SACRIFICE_ALTAR_BE = BLOCK_ENTITIES.register(Registration.SACRIFICE_ALTAR.getId().getPath(), () -> BlockEntityType.Builder.of(SacrificeAltarBE::new, Registration.SACRIFICE_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<WateryCradleBE>> WATERY_CRADLE_BE = BLOCK_ENTITIES.register(Registration.WATERY_CRADLE.getId().getPath(), () -> BlockEntityType.Builder.of(WateryCradleBE::new, Registration.WATERY_CRADLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MemorySieveBE>> MEMORY_SIEVE_BE = BLOCK_ENTITIES.register(Registration.MEMORY_SIEVE.getId().getPath(), () -> BlockEntityType.Builder.of(MemorySieveBE::new, Registration.MEMORY_SIEVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlaskBE>> FLASK_BE = BLOCK_ENTITIES.register(new ResourceLocation(References.MODID, "flask").getPath(), () -> BlockEntityType.Builder.of(FlaskBE::new, Registration.FLASK_LARGE.get(), Registration.FLASK_MEDIUM.get(), Registration.FLASK_SMALL.get(), Registration.JAR_LARGE.get(), Registration.JAR_MEDIUM.get(), Registration.JAR_SMALL.get(), Registration.FLASK_ITEM.get()).build(null));
    public static final RegistryObject<BlockEntityType<AlembicsBE>> ALEMBICS_BE = BLOCK_ENTITIES.register(Registration.ALEMBICS.getId().getPath(), () -> BlockEntityType.Builder.of(AlembicsBE::new, Registration.ALEMBICS.get()).build(null));
    public static final RegistryObject<BlockEntityType<SurgeryBedBE>> SURGERY_BED_BE = BLOCK_ENTITIES.register(Registration.SURGERY_BED.getId().getPath(), () -> BlockEntityType.Builder.of(SurgeryBedBE::new, Registration.SURGERY_BED.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlaskShelfBE>> FLASK_SHELF_BE = BLOCK_ENTITIES.register(Registration.FLASK_SHELF.getId().getPath(), () -> BlockEntityType.Builder.of(FlaskShelfBE::new, Registration.FLASK_SHELF.get()).build(null));
    public static final RegistryObject<BlockEntityType<FumeSpreaderBE>> FUME_SPREADER_BE = BLOCK_ENTITIES.register(Registration.FUME_SPREADER.getId().getPath(), () -> BlockEntityType.Builder.of(FumeSpreaderBE::new, Registration.FUME_SPREADER.get()).build(null));
    public static final RegistryObject<BlockEntityType<HeartBE>> HEART_BE = BLOCK_ENTITIES.register(Registration.HEART.getId().getPath(), () -> BlockEntityType.Builder.of(HeartBE::new, Registration.HEART.get()).build(null));
    public static final RegistryObject<BlockEntityType<SlugBaitBE>> SLUG_BAIT_BE = BLOCK_ENTITIES.register(Registration.SLUG_BAIT.getId().getPath(), () -> BlockEntityType.Builder.of(SlugBaitBE::new, Registration.SLUG_BAIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<GearBenchBE>> GEAR_BENCH_BE = BLOCK_ENTITIES.register(Registration.GEAR_BENCH.getId().getPath(), () -> BlockEntityType.Builder.of(GearBenchBE::new, Registration.GEAR_BENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloodWellBE>> BLOOD_WELL_BE = BLOCK_ENTITIES.register(Registration.BLOOD_WELL.getId().getPath(), () -> BlockEntityType.Builder.of(BloodWellBE::new, Registration.BLOOD_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<FleboBE>> FLEBO_BE = BLOCK_ENTITIES.register(Registration.FLEBO.getId().getPath(), () -> BlockEntityType.Builder.of(FleboBE::new, Registration.FLEBO.get()).build(null));
    public static final RegistryObject<BlockEntityType<DeepChestBE>> DEEP_CHEST_BE = BLOCK_ENTITIES.register(Registration.DEEP_CHEST.getId().getPath(), () -> BlockEntityType.Builder.of(DeepChestBE::new, Registration.DEEP_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<DreamFocusBE>> DREAM_FOCUS_BE = BLOCK_ENTITIES.register(Registration.DREAM_FOCUS.getId().getPath(), () -> BlockEntityType.Builder.of((pos, state) -> new DreamFocusBE(pos, state, DreamFocusBlock.FocusType.ITEM), Registration.DREAM_FOCUS.get()).build(null));
    public static final RegistryObject<BlockEntityType<DreamFocusBE>> DREAM_FOCUS_FLUID_BE = BLOCK_ENTITIES.register(Registration.DREAM_FOCUS_FLUIDS.getId().getPath(), () -> BlockEntityType.Builder.of((pos, state) -> new DreamFocusBE(pos, state, DreamFocusBlock.FocusType.FLUID), Registration.DREAM_FOCUS_FLUIDS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ArborealGeneratorBE>> ARBOREAL_GENERATOR_BE = BLOCK_ENTITIES.register(Registration.ARBOREAL_GENERATOR.getId().getPath(), () -> BlockEntityType.Builder.of(ArborealGeneratorBE::new, Registration.ARBOREAL_GENERATOR.get()).build(null));

    public static void init(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
