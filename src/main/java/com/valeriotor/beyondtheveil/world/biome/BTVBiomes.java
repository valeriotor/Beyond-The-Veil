package com.valeriotor.beyondtheveil.world.biome;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.placement.BTVPlacements;
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
    public static final ResourceKey<Biome> BLACK_SHORE = ResourceKey.create(Registries.BIOME, new ResourceLocation(References.MODID, "black_shore"));

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        context.register(ARCHE_CAVES, archeCaves(context));
        context.register(ARCHE_PLAINS, archePlains(context));
        context.register(BLACK_SHORE, blackShore(context));
    }

    public static Biome archeCaves(BootstapContext<Biome> context) {


        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BTVPlacements.BLACK_KELP);
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BTVPlacements.BLACK_SEAGRASS);

        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        commonArcheSpawns(mobspawnsettings$builder);
        mobspawnsettings$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.SEA_SNAKE.get(), 10, 1, 3));
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0).waterFogColor(0).fogColor(0).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

    }

    public static Biome archePlains(BootstapContext<Biome> context) {

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BTVPlacements.BLACK_KELP);
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BTVPlacements.BLACK_SEAGRASS);
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        commonArcheSpawns(mobspawnsettings$builder);
        mobspawnsettings$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.SANDFLATTER.get(), 4, 1, 1));
        //BiomeDefaultFeatures.oceanSpawns(mobspawnsettings$builder, 8, 4, 8);
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0).waterFogColor(0).fogColor(0).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

    }

    private static void commonArcheSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.CEPHALOPODIAN.get(), 1, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.ANGLER.get(), 10, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.SEPIID.get(), 30, 5, 12));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.ADELINE.get(), 4, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.BONECAGE.get(), 1, 1, 2));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.MAN_O_WAR.get(), 5, 1, 3));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.OCTID.get(), 20, 1, 6));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BTVEntities.UMANCALA.get(), 2, 1, 3));
    }

    public static Biome blackShore(BootstapContext<Biome> context) {

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BTVPlacements.BLACK_KELP);
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        //BiomeDefaultFeatures.oceanSpawns(mobspawnsettings$builder, 8, 4, 8);
        return (new Biome.BiomeBuilder()).hasPrecipitation(true).temperature(1F).downfall(0.7F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0).waterFogColor(0).fogColor(0).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();

    }


}
