package com.valeriotor.beyondtheveil.world.region;

import com.mojang.datafixers.util.Pair;
import com.valeriotor.beyondtheveil.world.biome.BTVBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class OverworldRegion extends Region {

    public OverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Climate.Parameter.span(-0.15F, 0.12F))
                .erosion(Climate.Parameter.span(0.55F, 1.0F))
                .depth(Climate.Parameter.span(-0.55F, 0.5F))
                .weirdness(Weirdness.VALLEY)
                .build().forEach(point -> builder.add(point, BTVBiomes.BLACK_SHORE));

        builder.build().forEach(mapper);
    }
}
