package com.valeriotor.beyondtheveil.world.placement;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.feature.BTVFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class BTVPlacements {

    public static final ResourceKey<PlacedFeature> BLACK_KELP = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(References.MODID, "black_kelp"));

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        final Holder<ConfiguredFeature<?, ?>> BLACK_KELP_CONFIG = configuredFeatureGetter.getOrThrow(BTVFeatures.BLACK_KELP);

        context.register(BLACK_KELP, new PlacedFeature(BLACK_KELP_CONFIG, List.of(NoiseBasedCountPlacement.of(80, 80.0D, 0.0D), CountOnEveryLayerPlacement.of(1), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Registration.DARK_SAND.get())), BiomeFilter.biome())));

    }

}
