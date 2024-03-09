package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.world.biome.BTVBiomes;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import com.valeriotor.beyondtheveil.world.feature.BTVFeatures;
import com.valeriotor.beyondtheveil.world.placement.BTVPlacements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BTVWorldGen extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, BTVFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, BTVPlacements::bootstrap)
            .add(Registries.DIMENSION_TYPE, BTVDimensions::registerType)
            .add(Registries.LEVEL_STEM, BTVDimensions::registerStem)
            .add(Registries.BIOME, BTVBiomes::bootstrapBiomes);

    public BTVWorldGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, Set<String> modIds) {
        super(output, registries, BUILDER, modIds);
    }
}
