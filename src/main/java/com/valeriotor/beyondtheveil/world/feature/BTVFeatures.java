package com.valeriotor.beyondtheveil.world.feature;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.placement.BTVPlacements;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.minecraft.data.worldgen.features.FeatureUtils.createKey;

public class BTVFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_KELP = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(References.MODID, "black_kelp"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_SEAGRASS = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(References.MODID, "black_seagrass"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCHE_DEBRIS = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(References.MODID, "arche_debris"));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatureGetter = context.lookup(Registries.PLACED_FEATURE);

        final Holder<PlacedFeature> HOLDER = placedFeatureGetter.getOrThrow(BTVPlacements.BLACK_KELP);
        //final Holder<PlacedFeature> HOLDER_SEAGRASS = placedFeatureGetter.getOrThrow(BTVPlacements.BLACK_SEAGRASS);

        context.register(BLACK_KELP, new ConfiguredFeature<>(Registration.BLACK_KELP_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        context.register(BLACK_SEAGRASS, new ConfiguredFeature<>(Registration.BLACK_SEAGRASS_FEATURE.get(), new ProbabilityFeatureConfiguration(0.4F)));
        context.register(ARCHE_DEBRIS, new ConfiguredFeature<>(Registration.ARCHE_DEBRIS_FEATURE.get(), new ProbabilityFeatureConfiguration(0.01F)));
    }

}
