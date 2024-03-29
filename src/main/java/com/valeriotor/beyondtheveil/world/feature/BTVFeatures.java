package com.valeriotor.beyondtheveil.world.feature;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.placement.BTVPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.minecraft.data.worldgen.features.FeatureUtils.createKey;

public class BTVFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_KELP = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(References.MODID, "black_kelp"));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatureGetter = context.lookup(Registries.PLACED_FEATURE);

        final Holder<PlacedFeature> HOLDER = placedFeatureGetter.getOrThrow(BTVPlacements.BLACK_KELP);

        context.register(BLACK_KELP, new ConfiguredFeature<>(Registration.BLACK_KELP_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
    }

}
