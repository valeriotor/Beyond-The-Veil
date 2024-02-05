package com.valeriotor.beyondtheveil.world.dimension;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class BTVDimensions {

    public static final ResourceKey<LevelStem> ARCHE_KEY = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(References.MODID, "arche"));
    public static final ResourceKey<Level> ARCHE_LEVEL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(References.MODID, "arche"));
    public static final ResourceKey<DimensionType> ARCHE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(References.MODID, "arche_type"));

    public static void registerStem(BootstapContext<LevelStem> context) {
        //NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(new FixedBiomeSource(context.lookup(Registries.BIOME).getOrThrow(Biomes.SOUL_SAND_VALLEY)), context.lookup(Registries.NOISE_SETTINGS).getOrThrow(NoiseGeneratorSettings.OVERWORLD));
//
        //context.register(ARCHE_KEY, new LevelStem(context.lookup(Registries.DIMENSION_TYPE).getOrThrow(ARCHE_TYPE), generator));
    }

    public static void registerType(BootstapContext<DimensionType> context) {
        context.register(ARCHE_TYPE, new DimensionType(OptionalLong.of(18000),
                false,
                true,
                false,
                false,
                1,
                false,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.END_EFFECTS,
                0.0F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 3)));
    }

}
