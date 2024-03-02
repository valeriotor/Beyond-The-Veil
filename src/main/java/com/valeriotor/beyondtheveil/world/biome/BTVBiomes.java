package com.valeriotor.beyondtheveil.world.biome;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BTVBiomes {

    public static final ResourceKey<Biome> ARCHE_CAVES = ResourceKey.create(Registries.BIOME, new ResourceLocation(References.MODID, "arche_caves"));
    public static final ResourceKey<Biome> ARCHE_PLAINS = ResourceKey.create(Registries.BIOME, new ResourceLocation(References.MODID, "arche_plains"));

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        context.register(ARCHE_CAVES, archeCaves(context));
        context.register(ARCHE_PLAINS, archePlains(context));
    }

    public static Biome archeCaves(BootstapContext<Biome> context) {


        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0).waterFogColor(0).fogColor(0).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

    }

    public static Biome archePlains(BootstapContext<Biome> context) {

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeDefaultFeatures.addLukeWarmKelp(biomegenerationsettings$builder);
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.oceanSpawns(mobspawnsettings$builder, 8, 4, 8);
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0).waterFogColor(0).fogColor(0).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

    }


}
